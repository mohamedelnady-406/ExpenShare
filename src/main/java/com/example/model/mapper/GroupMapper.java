package com.example.model.mapper;

import com.example.model.dto.group.CreateGroupRequest;
import com.example.model.dto.group.GroupDto;
import com.example.model.entity.GroupEntity;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Singleton
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "jsr330")
public interface GroupMapper {
    @Mapping(target = "members", ignore = true)
    GroupEntity toEntity(CreateGroupRequest req);

    @Mapping(source = "e.id", target = "groupId")
    @Mapping(source = "memberIds", target = "members")
    @Mapping(source = "e.createdAt", target = "createdAt")
    GroupDto toDto(GroupEntity e, List<Long> memberIds);
}
