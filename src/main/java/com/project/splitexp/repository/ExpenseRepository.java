package com.project.splitexp.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, UUID> {

}
