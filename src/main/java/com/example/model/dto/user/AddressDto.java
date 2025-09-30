package com.example.model.dto.user;

import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
@Serdeable
@Data
public class AddressDto {

    @NotBlank(message = "Line1 is required when address is present")
    @Size(max = 150, message = "Line1 cannot exceed 150 characters")
    private String line1;

    private String line2;

    @Size(max = 80)
    private String city;

    @Size(max = 80)
    private String state;

    @Size(max = 20)
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Country must be a valid ISO 3166-1 alpha-2 code")
    private String country;
}
