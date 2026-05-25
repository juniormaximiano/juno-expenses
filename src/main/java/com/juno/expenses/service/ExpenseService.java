package com.juno.expenses.service;

import com.juno.expenses.dto.CreateExpenseDTO;
import com.juno.expenses.dto.ResponseExpenseDTO;
import com.juno.expenses.model.Expense;
import com.juno.expenses.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public List<ResponseExpenseDTO> getAllExpenses() {
        var savedExpenses = expenseRepository.findAll();

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nothing found");
        }

        List<ResponseExpenseDTO> response = new ArrayList<>();

        for(Expense expense: savedExpenses){
            response.add(convertExpenseToDTO(expense));

        }
        return response;
    }

    public ResponseExpenseDTO convertExpenseToDTO(Expense expense) {
            ResponseExpenseDTO dto = new ResponseExpenseDTO(
                    expense.getId(),
                    expense.getDescription(),
                    expense.getCategory(),
                    expense.getPlace(),
                    expense.getDate(),
                    expense.getAmount()
            );
            return dto;
        }



}




