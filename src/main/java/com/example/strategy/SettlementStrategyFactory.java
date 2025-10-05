package com.example.strategy;

import com.example.model.entity.SettlementStrategyType;
import jakarta.inject.Singleton;

import java.util.Map;

@Singleton
public class SettlementStrategyFactory {
    private final Map<SettlementStrategyType, SettlementStrategy> strategyMap;
    public SettlementStrategyFactory(
            GreedyMinTransfersStrategy greedy,
            SmallestAmountsFirstStrategy small
    ) {
        this.strategyMap = Map.of(
                SettlementStrategyType.GREEDY_MIN_TRANSFERS, greedy,
                SettlementStrategyType.SMALLEST_AMOUNTS_FIRST, small
        );
    }
    public SettlementStrategy getStrategy(SettlementStrategyType type) {
        return strategyMap.getOrDefault(type, strategyMap.get(SettlementStrategyType.GREEDY_MIN_TRANSFERS));
    }
}
