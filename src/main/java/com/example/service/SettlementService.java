package com.example.service;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import com.example.event.KafkaProducer;
import com.example.event.model.EventMessage;
import com.example.exception.ConflictException;
import com.example.exception.NotFoundException;
import com.example.exception.ValidationException;
import com.example.model.dto.settlement.CreateSettlementRequest;
import com.example.model.dto.settlement.SettlementDto;
import com.example.model.entity.*;
import com.example.model.mapper.SettlementMapper;
import com.example.repository.facade.GroupRepositoryFacade;
import com.example.repository.facade.SettlementRepositoryFacade;
import com.example.repository.facade.UserRepositoryFacade;

import jakarta.inject.Singleton;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SettlementService {
    private final GroupRepositoryFacade      groupRepositoryFacade;
    private final UserRepositoryFacade       userRepositoryFacade;
    private final SettlementMapper           settlementMapper;
    private final SettlementRepositoryFacade settlementRepositoryFacade;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public SettlementDto addSettlement(CreateSettlementRequest request) {
        GroupEntity group    = groupRepositoryFacade.getGroupOrThrow(request.getGroupId());
        UserEntity  fromUser = userRepositoryFacade.getOrThrow(request.getFromUserId());
        UserEntity  toUser   = userRepositoryFacade.getOrThrow(request.getToUserId());

        if (Objects.equals(fromUser.getId(), toUser.getId())) {
            throw new ValidationException("Can't make self settlement");
        }

        boolean fromUserIsMember = groupRepositoryFacade.isMember(request.getGroupId(), request.getFromUserId());
        boolean toUserIsMember   = groupRepositoryFacade.isMember(request.getGroupId(), request.getToUserId());

        if (!fromUserIsMember ||!toUserIsMember) {
            throw new NotFoundException("one or two users are not members");
        }

        if (request.isEnforceOwedLimit()) {
            BigDecimal owed = calculateOwed(group, fromUser, toUser);

            if (request.getAmount().compareTo(owed) > 0) {
                throw new ValidationException("Cannot settle more than owed");
            }
        }

        SettlementEntity settlement = settlementMapper.toEntity(request);

        settlement.setGroup(group);
        settlement.setFromUser(fromUser);
        settlement.setToUser(toUser);

        SettlementEntity saved = settlementRepositoryFacade.saveSettlement(settlement);
        if(saved.getStatus() == Status.CONFIRMED){
            kafkaProducer.publishSettlementConfirmed(EventMessage.of(Map.of("settlementId",saved.getId(),
                    "groupId",saved.getGroup().getId(),"fromUserId",saved.getFromUser().getId(),
                    "toUserId",saved.getToUser().getId(),"amount",saved.getAmount())));
        }

        return settlementMapper.toDto(saved);
    }
    @Transactional
    public SettlementDto confirmSettlement(Long settlementId) {
        SettlementEntity entity = settlementRepositoryFacade.getByIdOrThrow(settlementId);
        if (entity.getStatus() == Status.CONFIRMED) {
            throw new ConflictException("Already confirmed");
        }
        if (entity.getStatus() == Status.CANCELED) {
            throw new ConflictException("Cannot confirm a canceled settlement");
        }
        entity.setStatus(Status.CONFIRMED);
        entity.setConfirmedAt(LocalDateTime.now());
        settlementRepositoryFacade.updateSettlment(entity);
        kafkaProducer.publishSettlementConfirmed(EventMessage.of(Map.of("settlementId",entity.getId(),
                "groupId",entity.getGroup().getId(),"fromUserId",entity.getFromUser().getId(),
        "toUserId",entity.getToUser().getId(),"amount",entity.getAmount())));

        return settlementMapper.toDto(entity);
    }
    @Transactional
    public SettlementDto cancelSettlement(Long settlementId) {
        SettlementEntity settlement = settlementRepositoryFacade.getByIdOrThrow(settlementId);

        if (settlement.getStatus() != Status.PENDING) {
            throw new ConflictException("Only pending settlements can be canceled");
        }

        settlement.setStatus(Status.CANCELED);
        settlementRepositoryFacade.updateSettlment(settlement);
        return settlementMapper.toDto(settlement);
    }

    private BigDecimal calculateOwed(GroupEntity group, UserEntity fromUser, UserEntity toUser) {
        BigDecimal owed = BigDecimal.ZERO;

        for (ExpenseEntity expense : group.getExpenses()) {

            // Only consider expenses paid by the "toUser"
            if (expense.getPaidBy().getId().equals(toUser.getId())) {
                for (ExpenseShareEntity share : expense.getShares()) {
                    if (share.getUser().getId().equals(fromUser.getId())) {

                        // Add how much fromUser owes in this expense
                        owed = owed.add(share.getShareAmount());
                    }
                }
            }
        }

        return owed.max(BigDecimal.ZERO);
    }



}
