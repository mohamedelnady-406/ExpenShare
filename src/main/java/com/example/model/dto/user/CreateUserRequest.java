package com.example.model.dto.user;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Serdeable
@ToString
@Getter
@Setter
@Data
public class CreateUserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$",
            message = "Mobile number must be in E.164 format (e.g. +201234567890)")
    private String mobileNumber;

    @Valid
    private AddressDto address;
}
