package com.project.splitexp.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.project.splitexp.repository.models.PendingTransaction;

public interface PendingTransactionsRepository extends CrudRepository<PendingTransaction, UUID> {

  public PendingTransaction findByPayerIdAndReceiverId(UUID payerId, UUID receiverId);

  public List<PendingTransaction> findByPayerId(UUID payerId);

  public List<PendingTransaction> findByReceiverId(UUID receiverId);
}
