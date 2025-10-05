package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.entity.SettlementEntity;
import com.example.model.entity.Status;
import com.example.repository.SettlementRepository;

import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.inject.Singleton;

import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SettlementRepositoryFacade {
    private final SettlementRepository settlementRepository;

    public SettlementEntity saveSettlement(SettlementEntity e) {
        return settlementRepository.save(e);
    }
    public SettlementEntity getByIdOrThrow(Long settlementId){
        return settlementRepository.findById(settlementId).orElseThrow(() -> new NotFoundException("Settlement not found"));
    }
    public SettlementEntity updateSettlment(SettlementEntity e){
        return settlementRepository.update(e);
    }

    public Page<SettlementEntity> findSettlementByFilters(Long groupId, Status status, Long aLong, Long aLong1, Pageable pageable) {
        return settlementRepository.findByFilters(groupId,status,aLong,aLong1,pageable);
    }
}


