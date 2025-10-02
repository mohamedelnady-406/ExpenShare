package com.example.controller;

import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.model.dto.settlement.CreateSettlementRequest;
import com.example.model.dto.settlement.SettlementDto;
import com.example.service.SettlementService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@Controller("/api/settlements")
@RequiredArgsConstructor
public class SettlementController {
    private final SettlementService settlementService;

    @Post
    public HttpResponse<?> addSettlement(@Body
    @Valid CreateSettlementRequest request) {
        SettlementDto dto = settlementService.addSettlement(request);

        return HttpResponse.created(dto);
    }
    @Post("/{settlementId}/confirm")
    public HttpResponse<SettlementDto> confirmSettlement(Long settlementId) {
        return HttpResponse.ok(settlementService.confirmSettlement(settlementId));
    }
    @Post("/{settlementId}/cancel")
    public HttpResponse<SettlementDto> cancelSettlement( Long settlementId) {
        return HttpResponse.ok(settlementService.cancelSettlement(settlementId));
    }
}
