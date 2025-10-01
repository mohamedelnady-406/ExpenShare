package com.example.model.dto.expense;

import com.example.model.entity.SplitType;
import io.micronaut.serde.annotation.Serdeable;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Serdeable
@Data
public class CreateExpenseRequest {
    @NotNull
    private Long groupId;

    @NotNull
    private Long paidBy;

    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank
    @Size(min = 1, max = 255)
    private String description;

    @NotNull
    private SplitType splitType;

    // For EXACT/PERCENT splits
    private List<Long> participants;

    private List<ShareRequest> shares;
}
