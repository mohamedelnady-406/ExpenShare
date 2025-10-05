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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Controller("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final KafkaProducer kafkaProducer;
    @Post
    public HttpResponse<GroupDto> createGroup(@Body @Valid CreateGroupRequest req) {
        GroupDto dto = groupService.createGroup(req);
        kafkaProducer.publishGroupCreated(EventMessage.of(Map.of("groupId", dto.getGroupId())));
        return HttpResponse.created(dto);
    }
    @Get("/{groupId}")
    public HttpResponse<GroupDto> getGroup(Long groupId){
        GroupDto dto = groupService.getGroup(groupId);
        return HttpResponse.ok(dto);
    }
    @Post("/{groupId}/members")
    public HttpResponse<AddMembersResponse> addMembers(Long groupId,
                                                       @Body @Valid AddMembersRequest request) {
        AddMembersResponse response = groupService.addMembers(groupId, request.getMembers());
        return HttpResponse.ok(response);
    }
    @Get("/{groupId}/balances")
    public GroupBalanceResponse getGroupBalances(Long groupId,
                                                 @QueryValue Optional<Instant> at) {
        Instant snapshot = at.orElse(Instant.now());
        return groupService.getGroupBalances(groupId, snapshot);
    }
    @Get("/{groupId}/settlements")
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
