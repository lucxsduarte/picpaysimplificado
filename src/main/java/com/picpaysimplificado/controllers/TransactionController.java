package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.services.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionServiceImpl transactionService;

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        final var transaction = this.transactionService.createTransaction(transactionDTO);
        return new ResponseEntity<>(transaction, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listAll() {
        final var list = this.transactionService.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/amount/{amount}")
    public ResponseEntity<List<Transaction>> findByAmount(@PathVariable final BigDecimal amount) {
        final var list = this.transactionService.findByAmount(amount);
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @GetMapping("/timestamp/{timestamp}")
    public ResponseEntity<List<Transaction>> findByTimestamp(@PathVariable final String timestamp) {
        final var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        final var list = this.transactionService.findByTimestamp(LocalDate.parse(timestamp, formatter));
        if (list.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

}
