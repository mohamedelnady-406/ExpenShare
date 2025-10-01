package com.example.service;

import com.example.exception.ValidationException;
import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.model.dto.expense.ExpenseDto;
import com.example.model.dto.expense.ShareDto;
import com.example.model.dto.expense.ShareRequest;
import com.example.model.entity.ExpenseEntity;
import com.example.model.entity.ExpenseShareEntity;
import com.example.model.entity.GroupEntity;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.ExpenseMapper;
import com.example.repository.GroupMemberRepository;
import com.example.repository.facade.ExpenseRepositoryFacade;
import com.example.repository.facade.GroupRepositoryFacade;
import com.example.repository.facade.UserRepositoryFacade;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepositoryFacade expenseRepositoryFacade;
    private final GroupRepositoryFacade groupRepositoryFacade;
    private final UserRepositoryFacade userRepositoryFacade;
    private final ExpenseMapper expenseMapper;
    public ExpenseDto addExpense(CreateExpenseRequest req){
        GroupEntity group = groupRepositoryFacade.getGroupOrThrow(req.getGroupId());
        UserEntity paidBy = userRepositoryFacade.getOrThrow(req.getPaidBy());
        boolean isMember = groupRepositoryFacade.isMember(group.getId(), paidBy.getId());
        if (!isMember) {
            throw new ValidationException("PaidBy user is not a member of this group");
        }
        ExpenseEntity expense = expenseMapper.toEntity(req);
        expense.setGroup(group);
        expense.setPaidBy(paidBy);
        expense.setCreatedAt(LocalDateTime.now());
        List<ExpenseShareEntity> shares = buildShares(expense, req, group);
        ExpenseEntity saved = expenseRepositoryFacade.saveWithShares(expense, shares);
        List<ShareDto> shareDtos = shares.stream()
                .map(s -> new ShareDto(s.getUser().getId(), s.getShareAmount()))
                .toList();

        return expenseMapper.toDto(saved, shareDtos);

    }
    private List<ExpenseShareEntity> buildShares(ExpenseEntity expense,
                                                 CreateExpenseRequest req,
                                                 GroupEntity group){
        List<ExpenseShareEntity> result = new ArrayList<>();
        BigDecimal total = req.getAmount();
        switch (req.getSplitType()){
            case EQUAL ->{
                List<Long> participantIds = (req.getParticipants() != null && !req.getParticipants().isEmpty())
                        ? req.getParticipants()
                        : groupRepositoryFacade.findUserIdsByGroupId(group.getId());

                BigDecimal perHead = total.divide(BigDecimal.valueOf(participantIds.size()), 2, RoundingMode.HALF_UP);
                for (Long uid : participantIds) {
                    UserEntity user = userRepositoryFacade.getOrThrow(uid);
                    ExpenseShareEntity share = new ExpenseShareEntity();
                    share.setExpense(expense);
                    share.setUser(user);
                    // payer gets negative share (they paid more than their part)
                    BigDecimal shareValue = uid.equals(req.getPaidBy())
                            ? perHead.subtract(total) // negative for payer
                            : perHead;
                    share.setShareAmount(shareValue);
                    result.add(share);
                }

            }
            case EXACT -> {
                BigDecimal sum = req.getShares().stream()
                        .map(ShareRequest::getAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (sum.compareTo(total) != 0) {
                    throw new ValidationException("Split amounts must total " + total);
                }
                for (ShareRequest sr : req.getShares()) {
                    UserEntity user = userRepositoryFacade.getOrThrow(sr.getUserId());
                    ExpenseShareEntity share = new ExpenseShareEntity();
                    share.setExpense(expense);
                    share.setUser(user);
                    share.setShareAmount(sr.getAmount());
                    result.add(share);
                }
            }

            case PERCENT -> {
                int percentSum = req.getShares().stream()
                        .mapToInt(ShareRequest::getPercent)
                        .sum();
                if (percentSum != 100) {
                    throw new ValidationException("Split percentages must total 100");
                }
                for (ShareRequest sr : req.getShares()) {
                    UserEntity user = userRepositoryFacade.getOrThrow(sr.getUserId());
                    ExpenseShareEntity share = new ExpenseShareEntity();
                    share.setExpense(expense);
                    share.setUser(user);
                    BigDecimal value = total
                            .multiply(BigDecimal.valueOf(sr.getPercent()))
                            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
                    share.setShareAmount(value);
                    result.add(share);
                }
            }
        }
        return result;
        }
}
