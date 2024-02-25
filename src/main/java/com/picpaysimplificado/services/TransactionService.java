package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.transaction.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    Transaction save(Transaction transaction);
    void delete(Long id);
    Transaction findById(Long id);
    List<Transaction> listAll();
    List<Transaction> findByTimestamp(LocalDate timestamp);
    List<Transaction> findByAmount(BigDecimal amount);
}
