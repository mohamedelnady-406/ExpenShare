package com.example.model.dto.settlement;

import com.example.model.entity.SettlementStrategyType;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
public class SuggestionRequest {
    private SettlementStrategyType strategy;
    private BigDecimal roundTo;
}
