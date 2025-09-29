package com.example.repository;

import com.example.model.entity.SettlementEntity;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
@Repository
public interface SettlementRepository extends JpaRepository<SettlementEntity,Long> {
}
