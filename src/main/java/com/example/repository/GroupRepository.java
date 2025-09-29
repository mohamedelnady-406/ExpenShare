package com.example.repository;

import com.example.model.entity.GroupEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity,Long> {
}
