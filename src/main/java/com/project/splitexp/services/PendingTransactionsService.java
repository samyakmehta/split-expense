package com.project.splitexp.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.splitexp.repository.PendingTransactionsRepository;
import com.project.splitexp.repository.models.PendingTransaction;
import com.project.splitexp.response.models.PendingTransactionResponse;
import com.project.splitexp.response.models.PendingTransactionsResponse;

@Service
public class PendingTransactionsService {

  public static final String PAY = "pay";
  public static final String RECEIVE = "receive";

  private final PendingTransactionsRepository pendingTransactionsRepository;

  public PendingTransactionsService(PendingTransactionsRepository pendingTransactionsRepository) {

    this.pendingTransactionsRepository = pendingTransactionsRepository;
  }

  public PendingTransactionsResponse getPendingTransactions(String userId) {

    List<PendingTransaction> pendingTransactionsAsPayer = pendingTransactionsRepository
        .findByPayerId(UUID.fromString(userId));

    List<PendingTransaction> pendingTransactionsAsReceiver = pendingTransactionsRepository
        .findByReceiverId(UUID.fromString(userId));

    Set<PendingTransactionResponse> pendingTransactionsResponse = new HashSet<PendingTransactionResponse>();

    // optimize Pending transactions between same users. Only the effective
    // transaction between two users should be added in the response

    for (PendingTransaction pendingTransactionAsPayer : pendingTransactionsAsPayer) {

      UUID receiverUser = pendingTransactionAsPayer.getReceiverId();

      PendingTransaction transactionTobeReceived = pendingTransactionsAsReceiver.stream()
          .filter(pt -> pt.getPayerId().equals(receiverUser)).findFirst().orElse(null);

      PendingTransactionResponse ptr = null;
      if (transactionTobeReceived != null) {
        if (pendingTransactionAsPayer.getAmount() == transactionTobeReceived.getAmount()) {
          continue;
        }

        if (pendingTransactionAsPayer.getAmount() < transactionTobeReceived.getAmount()) {
          ptr = new PendingTransactionResponse(
              transactionTobeReceived.getAmount() - pendingTransactionAsPayer.getAmount(), RECEIVE, receiverUser);
        } else {
          ptr = new PendingTransactionResponse(
              pendingTransactionAsPayer.getAmount() - transactionTobeReceived.getAmount(), PAY, receiverUser);
        }

        if (ptr != null) {
          pendingTransactionsResponse.add(ptr);
        }

        pendingTransactionsAsReceiver.remove(transactionTobeReceived);
      } else {
        ptr = new PendingTransactionResponse(pendingTransactionAsPayer.getAmount(), PAY, receiverUser);
        pendingTransactionsResponse.add(ptr);
      }

    }

    for (PendingTransaction pendingTransactionAsReceiver : pendingTransactionsAsReceiver) {
      PendingTransactionResponse ptr = new PendingTransactionResponse(pendingTransactionAsReceiver.getAmount(), RECEIVE,
          pendingTransactionAsReceiver.getPayerId());
      pendingTransactionsResponse.add(ptr);
    }

    return new PendingTransactionsResponse(UUID.fromString(userId), pendingTransactionsResponse);

  }

  @Transactional(rollbackOn = Exception.class)
  public void settlePendingTransactions(String payerId, String receiverId) {

    PendingTransaction pendingTransactionAsPayer = pendingTransactionsRepository
        .findByPayerIdAndReceiverId(UUID.fromString(payerId), UUID.fromString(receiverId));

    if (pendingTransactionAsPayer != null) {
      pendingTransactionsRepository.deleteById(pendingTransactionAsPayer.getId());
    }

    PendingTransaction pendingTransactionAsReceiver = pendingTransactionsRepository
        .findByPayerIdAndReceiverId(UUID.fromString(receiverId), UUID.fromString(payerId));

    if (pendingTransactionAsReceiver != null) {
      pendingTransactionsRepository.deleteById(pendingTransactionAsReceiver.getId());
    }

  }
}
