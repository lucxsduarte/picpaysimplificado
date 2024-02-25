package com.picpaysimplificado.services;

import com.picpaysimplificado.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    User update(User user);
    void delete(Long id);
    User findById(Long id);
    List<User> listAll();
    Optional<User> findByDocument(String document);
    List<User> findByFirstName(String firstName);

}
