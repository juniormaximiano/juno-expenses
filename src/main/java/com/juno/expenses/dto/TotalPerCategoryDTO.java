package com.juno.expenses.dto;

import com.juno.expenses.model.Category;

import java.math.BigDecimal;

public record TotalPerCategoryDTO(
        Category category,
        BigDecimal amountPerCategory
) {
}
