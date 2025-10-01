package com.example.model.mapper;

import com.example.model.dto.expense.CreateExpenseRequest;
import com.example.model.dto.expense.ExpenseDto;
import com.example.model.dto.expense.ShareDto;
import com.example.model.entity.ExpenseEntity;
import jakarta.inject.Singleton;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Singleton
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "jsr330")
public interface ExpenseMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)  // will be set in service
    @Mapping(target = "paidBy", ignore = true) // will be set in service
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "shares", ignore = true)
    ExpenseEntity toEntity(CreateExpenseRequest req);

    @Mapping(target = "expenseId", source = "e.id")
    @Mapping(target = "groupId", source = "e.group.id")
    @Mapping(target = "paidBy", source = "e.paidBy.id")
    @Mapping(target = "split", source = "shares")
    ExpenseDto toDto(ExpenseEntity e, List<ShareDto> shares);
}

