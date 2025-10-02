package com.example.model.dto.group;

import com.example.model.dto.expense.ShareDto;
import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Serdeable
public class GroupBalanceResponse {
    private Long groupId;
    private List<ShareDto> balances;
    private Instant calculatedAt;
}