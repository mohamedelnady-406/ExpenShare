package com.example.strategy;

import com.example.model.dto.settlement.SettlementSuggestion;
import com.example.model.entity.UserBalance;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class SmallestAmountsFirstStrategy implements SettlementStrategy{
    @Override
    public List<SettlementSuggestion> suggestSettlements(List<UserBalance> balances, BigDecimal roundTo) {
        List<UserBalance> work = balances.stream()
                .map(b -> new UserBalance(b.getUserId(), b.getBalance()))
                .collect(Collectors.toList());

        List<SettlementSuggestion> suggestions = new ArrayList<>();

        work.sort(Comparator.comparing(b -> b.getBalance().abs()));
        for (UserBalance payer : work) {
            if (payer.getBalance().compareTo(BigDecimal.ZERO) <= 0) continue; // skip non-payers

            for (UserBalance receiver : work) {
                if (receiver.getBalance().compareTo(BigDecimal.ZERO) >= 0) continue; // skip non-receivers
                if (payer.getUserId().equals(receiver.getUserId())) continue;

                // how much receiver expects (positive number)
                BigDecimal receiverNeeded = receiver.getBalance().negate();
                BigDecimal transferAmount = payer.getBalance().min(receiverNeeded);

                // rounding if requested
                if (roundTo != null) {
                    transferAmount = transferAmount
                            .divide(roundTo, 0, RoundingMode.HALF_UP)
                            .multiply(roundTo);
                }

                if (transferAmount.compareTo(BigDecimal.ZERO) > 0) {
                    suggestions.add(new SettlementSuggestion(
                            payer.getUserId(),
                            receiver.getUserId(),
                            transferAmount));

                    // update balances: payer pays (decrease), receiver receives (increase toward zero)
                    payer.setBalance(payer.getBalance().subtract(transferAmount));
                    receiver.setBalance(receiver.getBalance().add(transferAmount));
                }

                if (payer.getBalance().compareTo(BigDecimal.ZERO) == 0) break; // payer settled
            }
        }
        return suggestions;
    }
}
