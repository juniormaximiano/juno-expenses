package com.juno.expenses.controller;

import com.juno.expenses.dto.*;
import com.juno.expenses.model.Category;
import com.juno.expenses.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseExpenseDTO createExpense(@RequestBody @Valid CreateExpenseDTO dto) {
        return this.expenseService.createExpense(dto);
    }

    @GetMapping
    public List<ResponseExpenseDTO> listAllExpenses() {
        return this.expenseService.getAllExpenses();
    }

    @GetMapping("/sorted")
    public List<ResponseExpenseDTO> findAllByOrderByDateDesc() {
        return this.expenseService.findAllByOrderByDateDesc();
    }

    @GetMapping("/{id}")
    public ResponseExpenseDTO findExpenseById(@PathVariable long id) {
        return this.expenseService.findExpenseById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable long id) {
        this.expenseService.deleteExpenseById(id);
    }

    @PutMapping("/{id}")
    public ResponseExpenseDTO updateExpenseById(
            @PathVariable long id,
            @RequestBody @Valid UpdateExpenseDTO dto
    ) {
        return this.expenseService.updateExpenseById(id, dto);
    }

    @GetMapping("/category/{category}")
    public List<ResponseExpenseDTO> findExpenseByCategory(
            @PathVariable Category category
    ) {
        return this.expenseService.findAllByCategory(category);
    }

    @GetMapping("/place/{place}")
    public List<ResponseExpenseDTO> findExpenseByPlace(
            @PathVariable String place
    ) {
        return this.expenseService.findAllByPlace(place);
    }

    @GetMapping("/total")
    public TotalExpenseDTO getTotalExpense() {
        return this.expenseService.getTotalExpense();
    }

    @GetMapping("/total/category/{category}")
    public TotalPerCategoryDTO getTotalExpenseByCategory(
            @PathVariable Category category
    ) {
        return this.expenseService.getTotalPerCategory(category);
    }

    @GetMapping("/expenses/date-range")
    public List<ResponseExpenseDTO> getExpensesByDateRange(LocalDate dateAfter, LocalDate dateBefore ) {
        return this.expenseService.findByDateBetween(dateAfter, dateBefore);
    }

    @GetMapping("/expenses/total/date-range")
    public TotalPerCategoryDTO getTotalExpensesByDateRange(Category category, LocalDate dateAfter, LocalDate dateBefore) {
        return this.expenseService.getTotalByPeriod(category, dateAfter, dateBefore);
    }

}


