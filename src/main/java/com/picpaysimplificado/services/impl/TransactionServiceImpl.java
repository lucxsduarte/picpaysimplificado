package com.picpaysimplificado.services.impl;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import com.picpaysimplificado.services.TransactionService;
import com.picpaysimplificado.services.exceptions.IntegrityViolation;
import com.picpaysimplificado.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository repository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationServiceImpl notificationService;

    public Transaction createTransaction(final TransactionDTO transaction) {
        final var sender = this.userService.findById(transaction.senderId());
        final var receiver = this.userService.findById(transaction.receiverId());

        userService.validateTransaction(sender, transaction.value());

        if (!this.authorizeTransaction(sender, transaction.value())) {
            throw new IntegrityViolation("Transação não autorizada");
        }

        final var newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDate.now());

        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.save(sender);
        this.userService.save(receiver);

        this.notificationService.sendNotification(sender, "Transação realizada com sucesso");
        this.notificationService.sendNotification(receiver, "Transação recebida com sucesso");

        return newTransaction;
    }

    public boolean authorizeTransaction(User sender, BigDecimal value) {
        final var authorizationResopnse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResopnse.getStatusCode().equals(HttpStatus.OK) && authorizationResopnse.getBody().get("message").equals("Autorizado")) {
            final var message = (String) authorizationResopnse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        }
        return false;
    }

    @Override
    public Transaction save(final Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public void delete(final Long id) {
        final var transaction = findById(id);
        repository.delete(transaction);
    }

    @Override
    public Transaction findById(final Long id) {
        Optional<Transaction> transaction = repository.findById(id);
        return transaction.orElseThrow(() -> new ObjectNotFound("Transação %s não encontrada".formatted(id)));
    }

    @Override
    public List<Transaction> listAll() {
        final var list = repository.findAll();
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma transação encontrada!");
        }
        return list;
    }

    @Override
    public List<Transaction> findByTimestamp(final LocalDate timestamp) {
        final var list = repository.findByTimestamp(timestamp);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma transação encontrada");
        }
        return list;
    }

    @Override
    public List<Transaction> findByAmount(final BigDecimal amount) {
        final var list = repository.findByAmount(amount);
        if (list.isEmpty()) {
            throw new ObjectNotFound("Nenhuma transação encontrada");
        }
        return list;
    }
}
