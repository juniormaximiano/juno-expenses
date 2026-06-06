package com.juno.expenses.dto;

import com.juno.expenses.model.Category;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateExpenseDTO(

        @NotBlank(message = "Description must not be blank")
        @Size(min = 3, max = 100, message = "Description must be between 3 and 100 characters")
        String description,

        @NotNull
        Category category,

        @Positive
        BigDecimal amount,

        @Positive(message = "Amount must be greater than zero")
        @PastOrPresent(message = "Date cannot be in the future")
        LocalDate date,

        @NotBlank
        @Size(min = 3, max = 100)
        String placeName
) {
}
