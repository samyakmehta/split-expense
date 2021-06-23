package com.project.splitexp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.splitexp.request.models.ExpenseCreationRequest;
import com.project.splitexp.response.models.ExpenseInformation;
import com.project.splitexp.services.ExpenseService;

@RestController
public class ExpenseController {

  private final ExpenseService expenseService;

  public ExpenseController(ExpenseService expenseService) {
    this.expenseService = expenseService;
  }

  @PostMapping("/v1/expense")
  public ExpenseInformation createExpense(@RequestBody ExpenseCreationRequest expenseCreationRequest) {

    return expenseService.createExpense(expenseCreationRequest);

  }

  @GetMapping("/v1/expense/{expenseId}")
  public ExpenseInformation getExpense(@PathVariable String expenseId) {

    return expenseService.getExpenseInformation(expenseId);

  }
}
