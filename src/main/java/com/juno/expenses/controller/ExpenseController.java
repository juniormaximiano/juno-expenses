package com.juno.expenses.controller;

import com.juno.expenses.dto.CreateExpenseDTO;
import com.juno.expenses.dto.ResponseExpenseDTO;
import com.juno.expenses.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Expenses")
public class ExpenseController {

     ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }



    @PostMapping
    public ResponseExpenseDTO createExpense(@RequestBody @Valid CreateExpenseDTO dto){
        return this.expenseService.createExpense(dto);
    }

}
