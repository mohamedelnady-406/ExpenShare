package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.dto.settlement.SettlementDto;
import com.example.model.entity.SettlementEntity;
import com.example.repository.SettlementRepository;

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
}


