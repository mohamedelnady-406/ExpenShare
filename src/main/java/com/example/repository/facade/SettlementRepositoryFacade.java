package com.example.repository.facade;

import com.example.repository.SettlementRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class SettlementRepositoryFacade {
    private final SettlementRepository settlementRepository;
}
