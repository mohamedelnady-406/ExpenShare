package com.example.repository.facade;

import com.example.model.entity.ExpenseEntity;
import com.example.model.entity.ExpenseShareEntity;
import com.example.repository.ExpenseRepository;
import com.example.repository.ExpenseShareRepository;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Singleton
@RequiredArgsConstructor
public class ExpenseRepositoryFacade {
    private final ExpenseShareRepository expenseShareRepository;
    private final ExpenseRepository expenseRepository;

    @Transactional
    public ExpenseEntity saveWithShares(ExpenseEntity expenseEntity,
                                        List<ExpenseShareEntity> shares){
        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);
        for (ExpenseShareEntity share : shares) {
            share.setExpense(savedExpense);
        }
        expenseShareRepository.saveAll(shares);
        savedExpense.getShares().clear();
        savedExpense.getShares().addAll(shares);
        return savedExpense;
    }
}
