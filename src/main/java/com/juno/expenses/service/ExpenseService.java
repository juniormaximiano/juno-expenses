package com.juno.expenses.service;

import com.juno.expenses.dto.CreateExpenseDTO;
import com.juno.expenses.dto.ResponseExpenseDTO;
import com.juno.expenses.model.Expense;
import com.juno.expenses.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;



    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;

    }

    public ResponseExpenseDTO createExpense(CreateExpenseDTO RequestDTO) {


        Expense expense = new Expense();
        expense.setDescription(RequestDTO.description());
        expense.setAmount(RequestDTO.amount());
        expense.setDate(LocalDate.now());
        expense.setPlace(RequestDTO.placeName());

        expense.setCategory(RequestDTO.category());
        var savedExpense = expenseRepository.save(expense);

        ResponseExpenseDTO dto = new ResponseExpenseDTO(
            savedExpense.getId(),
                savedExpense.getDescription(),
                savedExpense.getCategory(),
                savedExpense.getPlace(),
                savedExpense.getDate(),
                savedExpense.getAmount()
        );

        return dto;



    }



}
