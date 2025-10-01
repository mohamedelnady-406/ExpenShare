package com.example.repository;

import com.example.model.entity.GroupMemberEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMemberEntity,Long> {
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);
    List<Long> findUserIdByGroupId(Long groupId);

}
