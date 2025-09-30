package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.entity.UserEntity;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
    public UserEntity getOrThrow(Long id){
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    public List<UserEntity> getAllMembersById(List<Long> members){
        return  userRepository.findByIdIn(members);
    }

}
