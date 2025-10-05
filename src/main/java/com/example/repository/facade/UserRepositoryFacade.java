package com.example.repository.facade;

import com.example.exception.NotFoundException;
import com.example.model.entity.UserEntity;
import com.example.repository.UserRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class UserRepositoryFacade {
   private final UserRepository userRepository;
   @Transactional
    public UserEntity create(UserEntity e){
        return userRepository.save(e);
    }
    @Transactional
    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }
    @Transactional
    public UserEntity getOrThrow(Long id){
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }
    @Transactional
    public List<UserEntity> getAllMembersById(List<Long> members){
        return  userRepository.findByIdIn(members);
    }

}
