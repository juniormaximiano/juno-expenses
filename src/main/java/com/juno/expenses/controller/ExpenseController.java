package com.juno.expenses.controller;

import com.juno.expenses.dto.*;
import com.juno.expenses.model.Category;
import com.juno.expenses.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Expenses")
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

    @GetMapping("Order By Date Desc")
    public List<ResponseExpenseDTO> findAllByOrderByDateDesc() {
        return this.expenseService.findAllByOrderByDateAsc();
    }

    @GetMapping(path = "/{id}")
    public List<ResponseExpenseDTO> findExpenseById(@Valid @PathVariable long id) {
        return this.expenseService.findExpenseById(id);
    }

    @DeleteMapping
    public void deleteExpenseById(@Valid @RequestParam long id) {
        this.expenseService.deleteExpenseById(id);
    }

    @PutMapping
    public ResponseExpenseDTO updateExpenseById(@Valid @RequestParam long id, @RequestBody UpdateExpenseDTO dto) {
        return this.expenseService.updateExpenseById(id, dto);
    }

    @GetMapping("/category/")
    public List<ResponseExpenseDTO> findExpenseByCategory(@RequestParam(required = false) Category category) {
        return this.expenseService.findAllByCategory(category);
    }

    @GetMapping("/place/")
    public List<ResponseExpenseDTO> findExpenseByPlace(@RequestParam(required = false) String place) {
        return this.expenseService.findAllByPlace(place);
    }

    @GetMapping("/total/")
    public TotalExpenseDTO  getTotalExpense() {
        return this.expenseService.getTotalExpense();
    }

    @GetMapping("/expenses/total/category/{category}")
    public TotalPerCategoryDTO getTotalExpenseByCategory(@PathVariable Category category) {
        return this.expenseService.getTotalPerCategory(category);
    }

}
