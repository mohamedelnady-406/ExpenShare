package com.example.model.error;


import io.micronaut.serde.annotation.Serdeable;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Serdeable
public class ApiError {
    private String error;
    private String code;
}
