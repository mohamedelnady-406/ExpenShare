package com.example.strategy;

import com.example.model.dto.settlement.SettlementSuggestion;
import com.example.model.entity.UserBalance;

import java.math.BigDecimal;
import java.util.List;

public interface SettlementStrategy {
    List<SettlementSuggestion> suggestSettlements(List<UserBalance> balances, BigDecimal roundTo);
}
