package com.example.repository;

import com.example.model.entity.ExpenseEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity,Long> {
}
