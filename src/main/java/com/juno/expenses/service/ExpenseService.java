package com.juno.expenses.service;

import com.juno.expenses.dto.*;
import com.juno.expenses.model.Category;
import com.juno.expenses.model.Expense;
import com.juno.expenses.repository.ExpenseRepository;
import com.opencsv.CSVWriter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.StringWriter;
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

    public ResponseExpenseDTO createExpense(CreateExpenseDTO requestDTO) {
        Expense expense = new Expense();
        expense.setDescription(requestDTO.description());
        expense.setAmount(requestDTO.amount());
        expense.setDate(LocalDate.now());
        expense.setPlace(requestDTO.placeName());
        expense.setCategory(requestDTO.category());

        var savedExpense = expenseRepository.save(expense);

        return convertExpenseToDTO(savedExpense);
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
        return new ResponseExpenseDTO(
                expense.getId(),
                expense.getDescription(),
                expense.getCategory(),
                expense.getPlace(),
                expense.getDate(),
                expense.getAmount());
    }

    public List<ResponseExpenseDTO> findAllByOrderByDateDesc() {
        var savedExpenses = expenseRepository.findAllByOrderByDateDesc();

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Nothing found");
        }

        List<ResponseExpenseDTO> response = new ArrayList<>();
        for (Expense expense : savedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }
        return response;
    }

    public ResponseExpenseDTO findExpenseById(long id) {
        return expenseRepository.findById(id)
                .map(this::convertExpenseToDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));
    }

    public void deleteExpenseById(long id) {
        if (expenseRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found");
        }
        expenseRepository.deleteById(id);
    }

    public ResponseExpenseDTO updateExpenseById(long id, UpdateExpenseDTO updateDTO) {
        var expenseToUpdate = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense not found"));

        expenseToUpdate.setDescription(updateDTO.description());
        expenseToUpdate.setCategory(updateDTO.category());
        expenseToUpdate.setAmount(updateDTO.amount());
        expenseToUpdate.setDate(updateDTO.date());
        expenseToUpdate.setPlace(updateDTO.placeName());

        var savedExpense = expenseRepository.save(expenseToUpdate);
        return convertExpenseToDTO(savedExpense);
    }

    public List<ResponseExpenseDTO> findAllByCategory(Category category) {
        if (category == null) {
            return getAllExpenses();
        }

        var savedExpenses = expenseRepository.findAllByCategory(category);

        List<ResponseExpenseDTO> response = new ArrayList<>();
        for (Expense expense : savedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }
        return response;
    }

    public List<ResponseExpenseDTO> findAllByPlace(String place) {
        var searchedPlaces = expenseRepository.findAllByPlace(place);

        if (searchedPlaces.isEmpty()) {
            return getAllExpenses();
        }

        List<ResponseExpenseDTO> response = new ArrayList<>();
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

        var sumAmount = savedExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TotalExpenseDTO(sumAmount);
    }

    public TotalPerCategoryDTO getTotalPerCategory(Category category) {
        var savedExpenses = expenseRepository.findAllByCategory(category);

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        var sumAmount = savedExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TotalPerCategoryDTO(category, sumAmount);
    }

    public List<ResponseExpenseDTO> findByDateBetween(LocalDate dateAfter, LocalDate dateBefore) {
        if (dateAfter.isAfter(dateBefore)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }

        var searchedExpenses = expenseRepository.findByDateBetween(dateAfter, dateBefore);

        if (searchedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        List<ResponseExpenseDTO> response = new ArrayList<>();
        for (Expense expense : searchedExpenses) {
            response.add(convertExpenseToDTO(expense));
        }
        return response;
    }

    public TotalPerCategoryDTO getTotalByPeriod(Category category, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }

        var searchedExpenses = expenseRepository.findByDateBetween(startDate, endDate);

        if (searchedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        var sumAmount = searchedExpenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new TotalPerCategoryDTO(category, sumAmount);
    }

    public String exportAllExpenses() {

        var savedExpenses = expenseRepository.findAll();

        if (savedExpenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non existing expenses");
        }

        try {
            StringWriter sw = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(sw);

            String[] header = new String[]{
                    "id",
                    "description",
                    "category",
                    "amount",
                    "date",
                    "place"
            };

            csvWriter.writeNext(header);

            for (Expense expense : savedExpenses) {

                csvWriter.writeNext(new String[]{
                        String.valueOf(expense.getId()),
                        expense.getDescription(),
                        expense.getCategory().toString(),
                        String.valueOf(expense.getAmount()),
                        expense.getDate().toString(),
                        expense.getPlace()


                });




            }

            csvWriter.flush();
            return sw.toString();

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to generate CSV file");
        }


    }
}




