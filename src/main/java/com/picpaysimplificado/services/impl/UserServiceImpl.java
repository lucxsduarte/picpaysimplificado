package com.picpaysimplificado.services.impl;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.UserRepository;
import com.picpaysimplificado.services.UserService;
import com.picpaysimplificado.services.exceptions.IntegrityViolation;
import com.picpaysimplificado.services.exceptions.ObjectNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserType() == UserType.MERCHANT) {
            throw new IntegrityViolation("Usuário do tipo Lojista não está autorizado a realizar transações");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IntegrityViolation("Saldo insuficiente para realizar a transação");
        }
    }

    @Override
    public User save(final User user) {
        return repository.save(user);
    }

    @Override
    public User update(final User user) {
        return null;
    }

    @Override
    public void delete(final Long id) {

    }

    @Override
    public User findById(final Long id) {
        final var user = repository.findById(id);
        return user.orElseThrow(() -> new ObjectNotFound("Usuário não encontrado"));
    }

    @Override
    public List<User> listAll() {
        return null;
    }

    @Override
    public Optional<User> findByDocument(final String document) {
        return Optional.empty();
    }

    @Override
    public List<User> findByFirstName(final String firstName) {
        return null;
    }
}
