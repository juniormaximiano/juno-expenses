package com.juno.expenses.dto;

import com.juno.expenses.model.Category;


import java.math.BigDecimal;
import java.time.LocalDate;

public record ResponseExpenseDTO(

    long id,

    String description,

    Category category,

    String placeName,

    LocalDate date,

    BigDecimal amount

) {
}
