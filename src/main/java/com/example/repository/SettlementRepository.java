package com.example.repository;

import com.example.model.entity.SettlementEntity;
import com.example.model.entity.Status;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import jakarta.annotation.Nullable;

@Repository
public interface SettlementRepository extends JpaRepository<SettlementEntity, Long> {


    @Query(
            value = """
            SELECT s FROM SettlementEntity s
            WHERE s.group.id = :groupId
              AND (:status IS NULL OR s.status = :status)
              AND (:fromUserId IS NULL OR s.fromUser.id = :fromUserId)
              AND (:toUserId IS NULL OR s.toUser.id = :toUserId)
        """,
            countQuery = """
            SELECT COUNT(s) FROM SettlementEntity s
            WHERE s.group.id = :groupId
              AND (:status IS NULL OR s.status = :status)
              AND (:fromUserId IS NULL OR s.fromUser.id = :fromUserId)
              AND (:toUserId IS NULL OR s.toUser.id = :toUserId)
        """
    )
    Page<SettlementEntity> findByFilters(
            Long groupId,
            @Nullable Status status,
            @Nullable Long fromUserId,
            @Nullable Long toUserId,
            Pageable pageable
    );
}

