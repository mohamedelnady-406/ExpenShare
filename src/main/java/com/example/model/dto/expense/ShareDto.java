package com.example.model.dto.expense;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Serdeable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShareDto {
    private Long userId;
    private BigDecimal share;
}
