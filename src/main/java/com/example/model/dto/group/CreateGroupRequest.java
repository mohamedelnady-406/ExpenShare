package com.example.model.dto.group;

import java.util.List;

import io.micronaut.serde.annotation.Serdeable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Serdeable
@Data
public class CreateGroupRequest {
    @NotBlank(message = "Name is required")
    @Size(
        min     = 1,
        max     = 100,
        message = "Group name must be between 1 and 100 characters"
    )
    private String     name;
    @NotEmpty(message = "Group must have at least one member")
    private List<Long> members;
}

