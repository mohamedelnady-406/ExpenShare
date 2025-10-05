package com.example.model.dto.settlement;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable
public class SettlementSuggestion {
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
}
