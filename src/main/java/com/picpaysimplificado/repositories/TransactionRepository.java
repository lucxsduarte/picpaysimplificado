package com.picpaysimplificado.repositories;

import com.picpaysimplificado.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTimestamp(LocalDate timestamp);
    List<Transaction> findByAmount(BigDecimal amount);
}
