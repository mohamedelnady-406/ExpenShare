package com.example.model.dto.settlement;

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
public class GroupSettlementPageResponse {
    private Long groupId;
    private List<SettlementItem> items;
    private int page;
    private int size;
    private int total;
}