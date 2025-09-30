package com.example.model.mapper;


import com.example.model.dto.user.CreateUserRequest;
import com.example.model.dto.user.UserDto;
import com.example.model.entity.UserEntity;

import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Singleton
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "jsr330")
public interface UserMapper {

    @Mapping(source = "address.line1", target = "addrLine1")
    @Mapping(source = "address.line2", target = "addrLine2")
    @Mapping(source = "address.city", target = "addrCity")
    @Mapping(source = "address.state", target = "addrState")
    @Mapping(source = "address.postalCode", target = "addrPostal")
    @Mapping(source = "address.country", target = "addrCountry")
    UserEntity toEntity(CreateUserRequest req);

    @Mapping(source = "id", target = "userId")
    @Mapping(target = "address.line1", source = "addrLine1")
    @Mapping(target = "address.line2", source = "addrLine2")
    @Mapping(target = "address.city", source = "addrCity")
    @Mapping(target = "address.state", source = "addrState")
    @Mapping(target = "address.postalCode", source = "addrPostal")
    @Mapping(target = "address.country", source = "addrCountry")
    UserDto toDto(UserEntity entity);

}
