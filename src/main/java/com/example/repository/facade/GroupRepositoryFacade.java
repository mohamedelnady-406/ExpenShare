package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.entity.GroupEntity;
import com.example.model.entity.UserEntity;
import com.example.repository.GroupRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.util.List;

@Singleton
@RequiredArgsConstructor
public class GroupRepositoryFacade {
    private final GroupRepository groupRepository;
    private final UserRepositoryFacade userRepositoryFacade;
    public GroupEntity save (GroupEntity e){
        return groupRepository.save(e);
    }
    public boolean usersExist(List<Long> userIds){
        List <UserEntity> users =
                userRepositoryFacade.getAllMembersById(userIds);

        return users.size() == userIds.size();
    }
}
