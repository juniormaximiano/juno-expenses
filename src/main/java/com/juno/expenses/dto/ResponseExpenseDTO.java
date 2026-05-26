package com.juno.expenses.dto;

import com.juno.expenses.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseExpenseDTO(

    long id,

    @NotBlank
    String description,

    @NotBlank
    Category category,

    @NotBlank
    String placeName,

    LocalDate date,

    @NotNull
    @Positive
    BigDecimal amount

) {
}
