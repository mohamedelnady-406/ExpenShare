package com.example.service;

import com.example.exception.NotFoundException;
import com.example.model.dto.group.CreateGroupRequest;
import com.example.model.dto.group.GroupDto;
import com.example.model.entity.GroupEntity;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.GroupMapper;
import com.example.repository.facade.GroupRepositoryFacade;
import com.example.repository.facade.UserRepositoryFacade;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepositoryFacade groupRepositoryFacade;
    private final UserRepositoryFacade userRepositoryFacade;
    private final GroupMapper groupMapper;
    public GroupDto createGroup(CreateGroupRequest request){
        if (!groupRepositoryFacade.usersExist(request.getMembers())) {
            throw new NotFoundException("One or more users not found");
        }
        GroupEntity group = groupMapper.toEntity(request);
        GroupEntity saved = groupRepositoryFacade.save(group);
        return groupMapper.toDto(saved, request.getMembers());
    }

//    public GroupDto getGroup(Long id) {
//
//    }
}
