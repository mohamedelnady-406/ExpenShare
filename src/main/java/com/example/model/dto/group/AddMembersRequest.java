package com.example.model.dto.group;


import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;
@Serdeable
@Data
public class AddMembersRequest {
    @NotEmpty(message = "Members list cannot be empty")
    private List<Long> members;

}
