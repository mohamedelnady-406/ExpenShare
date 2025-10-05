package com.example.service;

import com.example.event.KafkaProducer;
import com.example.event.model.EventMessage;
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
import java.util.Map;

@Singleton
@RequiredArgsConstructor
public class UserService {
    private final UserRepositoryFacade userRepositoryFacade;
    private final UserMapper userMapper;
    private final KafkaProducer kafkaProducer;

    public UserDto createUser(CreateUserRequest userRequest){

        if (userRepositoryFacade.existsByEmail(userRequest.getEmail())) {
            throw new ConflictException("Email already exists");
        }
        UserEntity entity = userRepositoryFacade.create(userMapper.toEntity(userRequest));
        entity.setCreatedAt(LocalDateTime.now());
        kafkaProducer.publishUserCreated(EventMessage.of(Map.of("userId", entity.getId())));
        return userMapper.toDto(entity);
    }
    public UserDto getUserById(Long id){
        UserEntity entity = userRepositoryFacade.getOrThrow(id);
        return userMapper.toDto(entity);
    }

}
