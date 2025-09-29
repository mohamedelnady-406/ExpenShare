package com.example.repository.facade;

import com.example.repository.ExpenseShareRepository;
import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;

@Singleton
@RequiredArgsConstructor
public class ExpenseRepositoryFacade {
    private final ExpenseShareRepository expenseShareRepository;
}
