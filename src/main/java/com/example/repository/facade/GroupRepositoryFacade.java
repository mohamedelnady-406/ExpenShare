package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.entity.GroupEntity;
import com.example.model.entity.GroupMemberEntity;
import com.example.model.entity.UserEntity;
import com.example.repository.GroupMemberRepository;
import com.example.repository.GroupRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class GroupRepositoryFacade {
    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepositoryFacade userRepositoryFacade;
    public GroupEntity save (GroupEntity e){
        return groupRepository.save(e);
    }
    public boolean usersExist(List<Long> userIds){
        List <UserEntity> users =
                userRepositoryFacade.getAllMembersById(userIds);

        return users.size() == userIds.size();
    }
    public GroupEntity getGroupOrThrow(Long id){
        return groupRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Group not found"));

    }

    public List<Long> addAll(Long groupId, List<Long> userIds) {
        GroupEntity group = getGroupOrThrow(groupId);
        List<UserEntity> users = userRepositoryFacade.getAllMembersById(userIds);
        List<GroupMemberEntity> newMembers = new ArrayList<>();
        List<Long> addedIds = new ArrayList<>();

        for (UserEntity user : users) {
            boolean exists = groupMemberRepository.existsByGroupIdAndUserId(groupId, user.getId());
            if (!exists) {
                GroupMemberEntity member = GroupMemberEntity.builder()
                        .group(group)
                        .user(user)
                        .addedAt(LocalDateTime.now())
                        .build();
                newMembers.add(member);
                addedIds.add(user.getId());
            }
        }
        if (!newMembers.isEmpty()) {
            groupMemberRepository.saveAll(newMembers);
        }

        return addedIds;
    }
    public boolean isMember(Long groupId, Long userId){
        return groupMemberRepository.existsByGroupIdAndUserId(groupId,userId);
    }

    public List<Long> findUserIdsByGroupId(Long id) {
        return groupMemberRepository.findUserIdByGroupId(id);
    }
}
