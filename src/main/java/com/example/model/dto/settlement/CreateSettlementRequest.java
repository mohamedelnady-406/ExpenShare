package com.example.model.dto.settlement;

import com.example.model.entity.Method;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Serdeable
public class CreateSettlementRequest {
    @NotNull
    Long groupId;
    @NotNull
    Long fromUserId;
    @NotNull
    Long toUserId;
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    BigDecimal amount;
    Method method= Method.OTHER;

    @Size(max = 255)
    private String note;

    @Size(max = 64)
    private String reference;

    private boolean enforceOwedLimit = true;

}
