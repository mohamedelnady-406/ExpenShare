package com.example.repository.facade;

import com.example.repository.GroupRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class GroupRepositoryFacade {
    private final GroupRepository groupRepository;
}
