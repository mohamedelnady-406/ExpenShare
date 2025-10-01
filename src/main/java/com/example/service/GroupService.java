package com.example.service;

import com.example.exception.NotFoundException;
import com.example.model.dto.group.AddMembersResponse;
import com.example.model.dto.group.CreateGroupRequest;
import com.example.model.dto.group.GroupDto;
import com.example.model.entity.GroupEntity;
import com.example.model.entity.GroupMemberEntity;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.GroupMapper;
import com.example.repository.facade.GroupRepositoryFacade;
import com.example.repository.facade.UserRepositoryFacade;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Singleton
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepositoryFacade groupRepositoryFacade;
    private final UserRepositoryFacade userRepositoryFacade;
    private final GroupMapper groupMapper;
    @Transactional
    public GroupDto createGroup(CreateGroupRequest request){
        if (!groupRepositoryFacade.usersExist(request.getMembers())) {
            throw new NotFoundException("One or more users not found");
        }
        GroupEntity group = groupMapper.toEntity(request);
        GroupEntity savedGroup = groupRepositoryFacade.save(group);

        List<UserEntity> users = userRepositoryFacade.getAllMembersById(request.getMembers());
        Set<GroupMemberEntity> members = new HashSet<>();
        for (UserEntity user : users) {
            GroupMemberEntity gm = new GroupMemberEntity();
            gm.setGroup(savedGroup);
            gm.setUser(user);
            gm.setAddedAt(LocalDateTime.now());
            members.add(gm);
        }
        savedGroup.setMembers(members);

        // Save group again with members (cascade = ALL on GroupEntity.members handles it)
        GroupEntity savedWithMembers = groupRepositoryFacade.save(savedGroup);

        List<Long> memberIds = savedWithMembers.getMembers().stream()
                .map(gm -> gm.getUser().getId())
                .toList();

        return groupMapper.toDto(savedWithMembers, memberIds);
    }

    @Transactional
    public GroupDto getGroup(Long id) {
       GroupEntity entity= groupRepositoryFacade.getGroupOrThrow(id);
        List<Long> members = entity.getMembers().stream()
                .map(m -> m.getUser().getId())
                .toList();
       return groupMapper.toDto(entity,members);
    }

    @Transactional
    public AddMembersResponse addMembers(Long groupId, List<Long> userIds) {
        GroupEntity group = groupRepositoryFacade.getGroupOrThrow(groupId);

        if (!groupRepositoryFacade.usersExist(userIds)) {
            throw new NotFoundException("One or more users not found");
        }

        List<Long> added = groupRepositoryFacade.addAll(groupId, userIds);

        int totalMembers = group.getMembers().size();

        return new AddMembersResponse(groupId, added, totalMembers);
    }
}
