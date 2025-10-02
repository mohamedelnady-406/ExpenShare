package com.example.model.dto.settlement;

import com.example.model.entity.Method;
import com.example.model.entity.Status;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Serdeable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettlementDto {
    private Long settlementId;
    private Long groupId;
    private Long fromUserId;
    private Long toUserId;
    private BigDecimal amount;
    private Method method;
    private String note;
    private String reference;
    private Status status;
    private Instant createdAt;
}