package com.example.model.dto.group;

import io.micronaut.serde.annotation.Serdeable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Serdeable
public class AddMembersResponse {
    private Long groupId;
    private List<Long> membersAdded;
    private int totalMembers;
}
