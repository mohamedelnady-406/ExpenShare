package com.example.controller;

import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.service.ExpenseService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Controller("/api/expenses")
@RequiredArgsConstructor
@Tag(name = "Expense Management", description = "Endpoints for managing expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @Operation(summary = "Add a new expense", description = "Creates a new expense record")
    @Post
    public HttpResponse<?> addExpense(@Body CreateExpenseRequest request){
        return HttpResponse.created(expenseService.addExpense(request));
    }


}
