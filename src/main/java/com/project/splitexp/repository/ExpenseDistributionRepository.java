package com.project.splitexp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.ExpenseDistribution;

public interface ExpenseDistributionRepository extends CrudRepository<ExpenseDistribution, UUID> {

  public List<ExpenseDistribution> findByExpenseId(UUID expenseId);
}
