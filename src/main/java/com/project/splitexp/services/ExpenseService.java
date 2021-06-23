package com.project.splitexp.services;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.splitexp.repository.ExpenseDistributionRepository;
import com.project.splitexp.repository.ExpenseRepository;
import com.project.splitexp.repository.PendingTransactionsRepository;
import com.project.splitexp.repository.models.Expense;
import com.project.splitexp.repository.models.ExpenseDistribution;
import com.project.splitexp.repository.models.PendingTransaction;
import com.project.splitexp.repository.models.User;
import com.project.splitexp.request.models.ExpenseCreationRequest;
import com.project.splitexp.request.models.ExpenseDistributionRequest;
import com.project.splitexp.response.models.EventInformation;
import com.project.splitexp.response.models.ExpenseInformation;

@Service
public class ExpenseService {

  public static final int FIXED_AMOUNT_CONTRIBUTION = 1;
  public static final int PERCENTAGE_CONTRIBUTION = 2;

  private final UserEntityService userEntityService;
  private final EventService eventService;
  private final ExpenseRepository expenseRepository;
  private final ExpenseDistributionRepository expenseDistributionRepository;
  private final PendingTransactionsRepository pendingTransactionsRepository;

  @Autowired
  public ExpenseService(UserEntityService userEntityService, EventService eventService,
      ExpenseRepository expenseRepository, ExpenseDistributionRepository expenseDistributionRepository,
      PendingTransactionsRepository pendingTransactionsRepository) {
    this.userEntityService = userEntityService;
    this.eventService = eventService;
    this.expenseRepository = expenseRepository;
    this.expenseDistributionRepository = expenseDistributionRepository;
    this.pendingTransactionsRepository = pendingTransactionsRepository;
  }

  public ExpenseInformation getExpenseInformation(String expenseId) {

    Expense expense = expenseRepository.findById(UUID.fromString(expenseId)).orElse(null);

    if (expense == null) {
      // throw exception
    }

    List<ExpenseDistribution> expenseDistributions = expenseDistributionRepository.findByExpenseId(expense.getId());
    Set<ExpenseDistribution> expenseDistributionsResponse = new HashSet<ExpenseDistribution>(expenseDistributions);

    return new ExpenseInformation(expense.getId(), expense.getName(), expense.getUserId(), expense.getAmount(),
        expenseDistributionsResponse);
  }

  @Transactional(rollbackOn = Exception.class)
  public ExpenseInformation createExpense(ExpenseCreationRequest expenseCreationRequest) {

    validateExpenseCreationRequest(expenseCreationRequest);

    Expense expense = Expense.builder().amount(expenseCreationRequest.getAmount())
        .eventId(UUID.fromString(expenseCreationRequest.getEventId()))
        .userId(UUID.fromString(expenseCreationRequest.getUserId())).name(expenseCreationRequest.getName())
        .createdAt(OffsetDateTime.now()).build();

    expense = expenseRepository.save(expense);

    Set<ExpenseDistribution> expenseDistributions = new HashSet<ExpenseDistribution>();
    for (ExpenseDistributionRequest expenseDistributionRequest : expenseCreationRequest
        .getExpenseDistributionRequests()) {

      double userAmount = (expenseDistributionRequest.getContributionType() == FIXED_AMOUNT_CONTRIBUTION)
          ? expenseDistributionRequest.getContributionAmount()
          : expenseDistributionRequest.getContributionPercentage() * expense.getAmount() / 100;

      ExpenseDistribution expenseDistribution = ExpenseDistribution.builder()
          .contributionType(expenseDistributionRequest.getContributionType()).expenseId(expense.getId())
          .userContribution(userAmount).userId(UUID.fromString(expenseDistributionRequest.getUserId())).build();

      if (!expenseDistributionRequest.getUserId().equals(expenseCreationRequest.getUserId())) {
        PendingTransaction pendingTransaction = pendingTransactionsRepository.findByPayerIdAndReceiverId(
            UUID.fromString(expenseDistributionRequest.getUserId()),
            UUID.fromString(expenseCreationRequest.getUserId()));

        if (pendingTransaction != null) {
          double pendingAmount = pendingTransaction.getAmount();
          pendingTransaction.setAmount(pendingAmount + userAmount);
        } else {
          pendingTransaction = PendingTransaction.builder().amount(userAmount)
              .payerId(UUID.fromString(expenseDistributionRequest.getUserId()))
              .receiverId(UUID.fromString(expenseCreationRequest.getUserId())).createdAt(OffsetDateTime.now()).build();
        }
        pendingTransactionsRepository.save(pendingTransaction);
      }

      ExpenseDistribution ed = expenseDistributionRepository.save(expenseDistribution);
      expenseDistributions.add(ed);
    }

    return new ExpenseInformation(expense.getId(), expense.getName(), expense.getUserId(), expense.getAmount(),
        expenseDistributions);
  }

  private void validateExpenseCreationRequest(ExpenseCreationRequest expenseCreationRequest) {

    double expenseAmount = expenseCreationRequest.getAmount();
    String userId = expenseCreationRequest.getUserId();
    String eventId = expenseCreationRequest.getEventId();
    String name = expenseCreationRequest.getName();

    Set<ExpenseDistributionRequest> expenseDistributionRequests = expenseCreationRequest
        .getExpenseDistributionRequests();

    if (StringUtils.isEmpty(name)) {
      // throw exception
    }

    User user = userEntityService.getUser(userId);
    if (user == null) {
      // throw Exception
    }

    EventInformation eventInformation = eventService.getEventInformation(eventId);
    Set<UUID> eventUserIds = eventInformation.getUserIds();

    double totalExpense = 0;
    for (ExpenseDistributionRequest expenseDistributionRequest : expenseDistributionRequests) {

      if (!eventUserIds.contains(expenseDistributionRequest.getUserId())) {
        // throw new exception
      }

      if (expenseDistributionRequest.getContributionType() != FIXED_AMOUNT_CONTRIBUTION
          && expenseDistributionRequest.getContributionType() != PERCENTAGE_CONTRIBUTION) {
        // throw exception
      }
      double userAmount = (expenseDistributionRequest.getContributionType() == FIXED_AMOUNT_CONTRIBUTION)
          ? expenseDistributionRequest.getContributionAmount()
          : expenseDistributionRequest.getContributionPercentage() * expenseAmount / 100;

      totalExpense = totalExpense + userAmount;

    }

    if (Math.ceil(totalExpense) != expenseAmount) {
      // throw exception
    }

  }
}
