package com.example.model.dto.group;

import java.time.LocalDateTime;

import java.util.List;

import io.micronaut.serde.annotation.Serdeable;

import lombok.Data;

@Serdeable
@Data
public class GroupDto {
    private Long          groupId;
    private String        name;
    private List<Long>    members;
    private LocalDateTime createdAt;
}

