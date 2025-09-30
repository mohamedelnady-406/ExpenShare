package com.example.controller;

import com.example.model.dto.group.CreateGroupRequest;
import com.example.model.dto.group.GroupDto;
import com.example.service.GroupService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import jakarta.validation.GroupSequence;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@Controller("/api")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    @Post("/groups")
    public HttpResponse<GroupDto> createGroup(@Body @Valid CreateGroupRequest req) {
        GroupDto dto = groupService.createGroup(req);
        return HttpResponse.created(dto);
    }
//    @Get("/groups/{groupId}")
//    public HttpResponse<GroupDto> getGroup(Long id){
//        GroupDto dto = groupService.getGroup(id);
//        return HttpResponse.ok(dto);
//    }
}
