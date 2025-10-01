package com.example.controller;

import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.service.ExpenseService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import lombok.RequiredArgsConstructor;

@Controller("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @Post
    public HttpResponse<?> addExpense(@Body CreateExpenseRequest request){
        return HttpResponse.created(expenseService.addExpense(request));
    }



}
