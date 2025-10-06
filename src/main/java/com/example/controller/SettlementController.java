package com.example.controller;

import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.model.dto.settlement.CreateSettlementRequest;
import com.example.model.dto.settlement.SettlementDto;
import com.example.service.SettlementService;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

@Controller("/api/settlements")
@RequiredArgsConstructor
@Tag(name = "Settlement Management", description = "Endpoints for managing settlements between users")
public class SettlementController {
    private final SettlementService settlementService;

    @Post
    @Operation(summary = "Create a settlement", description = "Create a new settlement record between users")
    @ApiResponse(responseCode = "201", description = "Settlement created successfully",
            content = @Content(schema = @Schema(implementation = SettlementDto.class)))
    public HttpResponse<?> addSettlement(@Body
    @Valid CreateSettlementRequest request) {
        SettlementDto dto = settlementService.addSettlement(request);

        return HttpResponse.created(dto);
    }
    @Post("/{settlementId}/confirm")
    @Operation(summary = "Confirm a settlement", description = "Confirm a pending settlement by its ID")
    @ApiResponse(responseCode = "200", description = "Settlement confirmed successfully")
    public HttpResponse<SettlementDto> confirmSettlement(Long settlementId) {
        return HttpResponse.ok(settlementService.confirmSettlement(settlementId));
    }
    @Post("/{settlementId}/cancel")
    @Operation(summary = "Cancel a settlement", description = "Cancel a pending settlement by its ID")
    @ApiResponse(responseCode = "200", description = "Settlement cancelled successfully")
    public HttpResponse<SettlementDto> cancelSettlement( Long settlementId) {
        return HttpResponse.ok(settlementService.cancelSettlement(settlementId));
    }
}
