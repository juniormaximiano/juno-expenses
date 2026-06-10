package com.juno.expenses.controller;

import com.juno.expenses.dto.*;
import com.juno.expenses.model.Category;
import com.juno.expenses.service.ExpenseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expenses")
@Tag(name = "Expenses", description = "Endpoints for managing personal expenses")
public class ExpenseController {

    ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @Operation(summary = "Create a new expense record")
    @PostMapping
    public ResponseExpenseDTO createExpense(@RequestBody @Valid CreateExpenseDTO dto) {
        return this.expenseService.createExpense(dto);
    }

    @Operation(summary = "Retrieve all registered expenses")
    @GetMapping
    public List<ResponseExpenseDTO> listAllExpenses() {
        return this.expenseService.getAllExpenses();
    }

    @Operation(summary = "Retrieve all expenses sorted by date descending")
    @GetMapping("/sorted")
    public List<ResponseExpenseDTO> findAllByOrderByDateDesc() {
        return this.expenseService.findAllByOrderByDateDesc();
    }

    @Operation(summary = "Retrieve a specific expense by its identifier")
    @GetMapping("/{id}")
    public ResponseExpenseDTO findExpenseById(@PathVariable long id) {
        return this.expenseService.findExpenseById(id);
    }

    @Operation(summary = "Delete an expense by its identifier")
    @DeleteMapping("/{id}")
    public void deleteExpenseById(@PathVariable long id) {
        this.expenseService.deleteExpenseById(id);
    }

    @Operation(summary = "Update an existing expense")
    @PutMapping("/{id}")
    public ResponseExpenseDTO updateExpenseById(
            @PathVariable long id,
            @RequestBody @Valid UpdateExpenseDTO dto
    ) {
        return this.expenseService.updateExpenseById(id, dto);
    }

    @Operation(summary = "Retrieve expenses filtered by category")
    @GetMapping("/filter/category")
    public List<ResponseExpenseDTO> findExpenseByCategory(
            @RequestParam Category category
    ) {
        return this.expenseService.findAllByCategory(category);
    }

    @Operation(summary = "Retrieve expenses filtered by place name")
    @GetMapping("/filter/place")
    public List<ResponseExpenseDTO> findExpenseByPlace(
            @RequestParam String place
    ) {
        return this.expenseService.findAllByPlace(place);
    }

    @Operation(summary = "Calculate the total amount of all expenses")
    @GetMapping("/total")
    public TotalExpenseDTO getTotalExpense() {
        return this.expenseService.getTotalExpense();
    }

    @Operation(summary = "Calculate the total amount spent for a specific category")
    @GetMapping("/total/category")
    public TotalPerCategoryDTO getTotalExpenseByCategory(
            @RequestParam Category category
    ) {
        return this.expenseService.getTotalPerCategory(category);
    }

    @Operation(summary = "Retrieve expenses within a date range")
    @GetMapping("/filter/date-range")
    public List<ResponseExpenseDTO> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAfter,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateBefore
    ) {
        return this.expenseService.findByDateBetween(dateAfter, dateBefore);
    }

    @Operation(summary = "Calculate total expenses within a date range")
    @GetMapping("/total/date-range")
    public TotalPerCategoryDTO getTotalExpensesByDateRange(
            @RequestParam(required = false) Category category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateAfter,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateBefore
    ) {
        return this.expenseService.getTotalByPeriod(category, dateAfter, dateBefore);
    }

    @Operation(summary = "Export all expenses to a CSV file")
    @GetMapping("/export")
    public ResponseEntity<String> exportExpenses() {
        String fileName = "Expenses.csv";
        var csv = this.expenseService.exportAllExpenses();

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileName)
                .body(csv);
    }
}