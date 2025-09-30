package com.example.service;

import com.example.model.dto.user.CreateUserRequest;
import com.example.model.entity.UserEntity;
import com.example.model.mapper.UserMapper;
import com.example.repository.UserRepository;
import com.example.repository.facade.UserRepositoryFacade;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

@Singleton
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryFacade userRepositoryFacade;
    private final UserMapper userMapper;

    public UserEntity createUser(CreateUserRequest userRequest){
        UserEntity entity = userRepositoryFacade.create(userMapper.toEntity(userRequest));
        entity.setCreatedAt(LocalDateTime.now());
        return entity;
    }

}
