package com.example.repository;

import com.example.model.entity.ExpenseShareEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShareEntity,Long> {
}
