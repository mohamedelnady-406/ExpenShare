package com.example.repository;

import com.example.model.entity.GroupMemberEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity,Long> {
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);
}
