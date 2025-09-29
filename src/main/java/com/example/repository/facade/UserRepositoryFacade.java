package com.example.repository.facade;

import com.example.repository.UserRepository;
import io.micronaut.data.annotation.Repository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class UserRepositoryFacade {
   private final UserRepository userRepository;


}
