package com.example.controller;

import com.example.event.KafkaProducer;
import com.example.event.model.EventMessage;
import com.example.model.dto.group.*;
import com.example.model.dto.settlement.GroupSettlementPageResponse;
import com.example.model.dto.settlement.SuggestionRequest;
import com.example.model.dto.settlement.SuggestionResponse;
import com.example.model.entity.SettlementStrategyType;
import com.example.service.GroupService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import com.example.model.entity.Status;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Controller("/api/groups")
@RequiredArgsConstructor
@Tag(name = "Group Management", description = "Endpoints for creating and managing groups")
public class GroupController {
    private final GroupService groupService;
    private final KafkaProducer kafkaProducer;
    @Post
    @Operation(summary = "Create a group", description = "Creates a new expense sharing group")
    @ApiResponse(responseCode = "201", description = "Group successfully created",
            content = @Content(schema = @Schema(implementation = GroupDto.class)))
    public HttpResponse<GroupDto> createGroup(@Body @Valid CreateGroupRequest req) {
        GroupDto dto = groupService.createGroup(req);
        kafkaProducer.publishGroupCreated(EventMessage.of(Map.of("groupId", dto.getGroupId())));
        return HttpResponse.created(dto);
    }
    @Get("/{groupId}")
    @Operation(summary = "Get group details", description = "Retrieve group information by its ID")
    @ApiResponse(responseCode = "200", description = "Group details retrieved",
            content = @Content(schema = @Schema(implementation = GroupDto.class)))
    public HttpResponse<GroupDto> getGroup(Long groupId){
        GroupDto dto = groupService.getGroup(groupId);
        return HttpResponse.ok(dto);
    }
    @Post("/{groupId}/members")
    @Operation(summary = "Add members to a group", description = "Add multiple members to an existing group")
    @ApiResponse(responseCode = "200", description = "Members added successfully")
    public HttpResponse<AddMembersResponse> addMembers(Long groupId,
                                                       @Body @Valid AddMembersRequest request) {
        AddMembersResponse response = groupService.addMembers(groupId, request.getMembers());
        return HttpResponse.ok(response);
    }
    @Operation(summary = "Get group balances", description = "Get the balance of each member in the group at a specific point in time")
    @ApiResponse(responseCode = "200", description = "Balances retrieved",
            content = @Content(schema = @Schema(implementation = GroupBalanceResponse.class)))
    @Get("/{groupId}/balances")
    public GroupBalanceResponse getGroupBalances(Long groupId,
                                                 @QueryValue Optional<Instant> at) {
        Instant snapshot = at.orElse(Instant.now());
        return groupService.getGroupBalances(groupId, snapshot);
    }
    @Get("/{groupId}/settlements")
    @Operation(summary = "List group settlements", description = "List all settlements within a group with optional filters")
    @ApiResponse(responseCode = "200", description = "Settlements retrieved",
            content = @Content(schema = @Schema(implementation = GroupSettlementPageResponse.class)))

    public HttpResponse<GroupSettlementPageResponse> listGroupSettlements(
            @PathVariable Long groupId,
            @QueryValue(defaultValue = "") Optional<Status> status,
            @QueryValue(defaultValue = "") Optional<Long> fromUserId,
            @QueryValue(defaultValue = "") Optional<Long> toUserId,
            @QueryValue(defaultValue = "0") int page,
            @QueryValue(defaultValue = "20") int size
    ) {
        return HttpResponse.ok(
                groupService.listGroupSettlements(groupId, status, fromUserId, toUserId, page, size)
        );
    }
    @Post("/{groupId}/settlements/suggest")
    @Operation(summary = "Suggest settlements", description = "Suggest optimal settlements for a group using a selected strategy")
    @ApiResponse(responseCode = "200", description = "Suggestions generated successfully",
            content = @Content(schema = @Schema(implementation = SuggestionResponse.class)))
    public HttpResponse<SuggestionResponse> suggestSettlements(
            Long groupId,
            @Body SuggestionRequest request
    ) {
        var response = groupService.suggest(
                groupId,
                request.getStrategy() != null ? request.getStrategy() : SettlementStrategyType.GREEDY_MIN_TRANSFERS,
                request.getRoundTo()
        );
        return HttpResponse.ok(response);
    }
}
