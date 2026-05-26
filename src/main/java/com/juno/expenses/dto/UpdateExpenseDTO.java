package com.juno.expenses.dto;

import com.juno.expenses.model.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateExpenseDTO(
        @NotNull
        String description,

        @NotNull
        Category category,

        @Positive
        BigDecimal amount,

        @NotNull
        LocalDate date,

        @NotNull
        String placeName
) {
}
