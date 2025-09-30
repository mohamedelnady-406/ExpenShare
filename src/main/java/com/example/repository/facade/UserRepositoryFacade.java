package com.example.repository.facade;

import com.example.model.entity.UserEntity;
import com.example.repository.UserRepository;
import io.micronaut.data.annotation.Repository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class UserRepositoryFacade {
   private final UserRepository userRepository;
    public UserEntity create(UserEntity e){
        return userRepository.save(e);
    }
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

}
