package com.juno.expenses.repository;

import com.juno.expenses.dto.ResponseExpenseDTO;
import com.juno.expenses.model.Category;
import com.juno.expenses.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findAllByOrderByDateDesc();

    List<Expense> findAllByCategory(Category category);

    List<Expense> findAllByPlace(String place);
}
