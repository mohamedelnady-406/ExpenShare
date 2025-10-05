package com.example.model.dto.settlement;

import com.example.model.entity.SettlementStrategyType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable
public class SuggestionResponse {
    private Long groupId;
    private List<SettlementSuggestion> suggestions;
    private int totalTransfers;
    private SettlementStrategyType strategy;
}
