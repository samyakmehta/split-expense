package com.project.splitexp.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.splitexp.request.models.SettlementRequest;
import com.project.splitexp.response.models.PendingTransactionsResponse;
import com.project.splitexp.services.PendingTransactionsService;

@RestController
public class SettlementController {

  private final PendingTransactionsService pendingTransactionsService;

  public SettlementController(PendingTransactionsService pendingTransactionsService) {
    this.pendingTransactionsService = pendingTransactionsService;
  }

  @GetMapping("/v1/settlements/{userId}/pending")
  public PendingTransactionsResponse getPendingTransactionsForUser(@PathVariable String userId) {

    return pendingTransactionsService.getPendingTransactions(userId);
  }

  @PostMapping("/v1/settle")
  public void settleTransactionsBetweenUsers(@RequestBody SettlementRequest settlementRequest) {
    pendingTransactionsService.settlePendingTransactions(settlementRequest.getUserOneId(),
        settlementRequest.getUserTwoId());
  }

}
