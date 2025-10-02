package com.example.model.dto.expense;

import io.micronaut.http.annotation.Get;
import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.math.BigDecimal;

@Serdeable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareDto {
    private Long userId;
    private BigDecimal share;
}
