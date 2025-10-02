package com.example.controller;

import com.example.model.dto.group.*;
import com.example.service.GroupService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.awt.*;
import java.time.Instant;
import java.util.Optional;

@Controller("/api/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @Post
    public HttpResponse<GroupDto> createGroup(@Body @Valid CreateGroupRequest req) {
        GroupDto dto = groupService.createGroup(req);
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
}
