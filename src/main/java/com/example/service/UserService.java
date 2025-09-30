package com.example.service;

import com.example.exception.ConflictException;
import com.example.exception.ValidationException;
import com.example.model.dto.user.CreateUserRequest;
import com.example.model.dto.user.UserDto;
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

    public UserDto createUser(CreateUserRequest userRequest){

        if (userRepositoryFacade.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        UserEntity entity = userRepositoryFacade.create(userMapper.toEntity(userRequest));
        entity.setCreatedAt(LocalDateTime.now());
        return userMapper.toDto(entity);
    }
    public UserDto getUserById(Long id){
        UserEntity entity = userRepositoryFacade.getOrThrow(id);
        return userMapper.toDto(entity);
    }

}
