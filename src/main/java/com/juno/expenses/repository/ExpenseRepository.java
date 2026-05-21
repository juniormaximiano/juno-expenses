package com.juno.expenses.repository;

import com.juno.expenses.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository  extends JpaRepository<Expense, Long> {
}
