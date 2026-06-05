package com.juno.expenses.service;

import com.juno.expenses.dto.*;
import com.juno.expenses.model.Category;
import com.juno.expenses.model.Expense;
import com.juno.expenses.repository.ExpenseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
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
                savedExpense.getAmount());

        return dto;


    }

    public List<ResponseExpenseDTO> getAllExpenses() {
        var savedExpenses = expenseRepository.findAll();

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nothing found");
        }

        List<ResponseExpenseDTO> response = new ArrayList<>();

        for (Expense expense : savedExpenses) {
            response.add(convertExpenseToDTO(expense));

        }
        return response;
    }

    public ResponseExpenseDTO convertExpenseToDTO(Expense expense) {
        ResponseExpenseDTO dto = new ResponseExpenseDTO(expense.getId(), expense.getDescription(), expense.getCategory(), expense.getPlace(), expense.getDate(), expense.getAmount());
        return dto;
    }

    public List<ResponseExpenseDTO> findAllByOrderByDateDesc() {

        List<ResponseExpenseDTO> response = new ArrayList<>();

        var savedExpenses = expenseRepository.findAllByOrderByDateDesc();

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nothing found");
        }

        for (Expense expense : savedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }

        return response;

    }

    public List<ResponseExpenseDTO> findExpenseById(long id) {

        List<ResponseExpenseDTO> response = new ArrayList<>();

        var searchedExpense = expenseRepository.findById(id);

        if (searchedExpense.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");

        } else {

            response.add(convertExpenseToDTO(searchedExpense.get()));

        }

        return response;

    }

    public void deleteExpenseById(long id) {

        var expenseSearched = expenseRepository.findById(id);

        if (expenseSearched.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        expenseRepository.deleteById(id);


    }

    public ResponseExpenseDTO updateExpenseById(long id, UpdateExpenseDTO UpDTO) {

        var searchedExpense = expenseRepository.findById(id);


        if (searchedExpense.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }


        var ExpenseToUpdate = searchedExpense.get();
        ExpenseToUpdate.setDescription(UpDTO.description());
        ExpenseToUpdate.setCategory(UpDTO.category());
        ExpenseToUpdate.setAmount(UpDTO.amount());
        ExpenseToUpdate.setDate(UpDTO.date());
        ExpenseToUpdate.setPlace(UpDTO.placeName());


        var savedExpense = expenseRepository.save(ExpenseToUpdate);

        return convertExpenseToDTO(savedExpense);

    }

    public List<ResponseExpenseDTO> findAllByCategory(Category category) {

        var savedExpenses = expenseRepository.findAllByCategory(category);

        List<ResponseExpenseDTO> response = new ArrayList<>();

        if (category == null) {
            return getAllExpenses();
        }

        for (Expense expense : savedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }

        return response;


    }

    public List<ResponseExpenseDTO> findAllByPlace(String place) {

        var searchedPlaces = expenseRepository.findAllByPlace(place);

        List<ResponseExpenseDTO> response = new ArrayList<>();


        if (searchedPlaces.isEmpty()) {
            getAllExpenses();
        }

        for (Expense expense : searchedPlaces) {
            response.add(convertExpenseToDTO(expense));
        }
        return response;
    }

    public TotalExpenseDTO getTotalExpense() {

        var savedExpenses = expenseRepository.findAll();

        if (savedExpenses.isEmpty()) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");

        }


        var sumAmount = BigDecimal.ZERO;

        for (Expense expense : savedExpenses) {

            sumAmount = sumAmount.add(expense.getAmount());

        }

        TotalExpenseDTO dto = new TotalExpenseDTO(sumAmount);

        return dto;


    }

    public TotalPerCategoryDTO getTotalPerCategory(Category category) {

        var savedExpenses = expenseRepository.findAllByCategory(category);

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        var sumAmount = BigDecimal.ZERO;

        for (Expense expense : savedExpenses) {
            sumAmount = sumAmount.add(expense.getAmount());

        }

        TotalPerCategoryDTO dto = new TotalPerCategoryDTO(category, sumAmount);
        return dto;


    }

    public List<ResponseExpenseDTO> findByDateBetween(LocalDate dateAfter, LocalDate dateBefore) {

        List<ResponseExpenseDTO> response = new ArrayList<>();

        var searchedExpenses = expenseRepository.findByDateBetween(dateAfter, dateBefore);

        if (searchedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        for (Expense expense : searchedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }
        return response;


    }

    public TotalPerCategoryDTO getTotalByPeriod(Category category,LocalDate startDate, LocalDate endDate) {

        var searchedExpenses = expenseRepository.findByDateBetween(startDate, endDate);

        if (searchedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        var sumAmount = BigDecimal.ZERO;

        for (Expense expense : searchedExpenses) {
            sumAmount = sumAmount.add(expense.getAmount());
        }

        TotalPerCategoryDTO dto = new TotalPerCategoryDTO(category, sumAmount);

        return dto;
    }
}




