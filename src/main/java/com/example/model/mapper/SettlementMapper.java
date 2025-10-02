package com.example.model.mapper;

import com.example.model.dto.settlement.CreateSettlementRequest;
import com.example.model.dto.settlement.SettlementDto;
import com.example.model.entity.SettlementEntity;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Singleton
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "jsr330")
public interface SettlementMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "fromUser", ignore = true)
    @Mapping(target = "toUser", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    SettlementEntity toEntity(CreateSettlementRequest req);
    @Mapping(target = "settlementId", source = "id")
    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "fromUserId", source = "fromUser.id")
    @Mapping(target = "toUserId", source = "toUser.id")
    @Mapping(target = "createdAt", expression = "java(e.getCreatedAt().atZone(java.time.ZoneOffset.UTC).toInstant())")
    SettlementDto toDto(SettlementEntity e);

}
