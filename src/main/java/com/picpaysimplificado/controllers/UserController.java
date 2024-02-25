package com.picpaysimplificado.controllers;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO user) {
        var newUser = new User(user);
        userService.save(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        var newUser = new User(user);
        newUser.setId(id);
        userService.update(newUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> listAll() {
        final var list = userService.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/document/{document}")
    public ResponseEntity<Optional<User>> findByDocumento(@PathVariable String document) {
        final var user = userService.findByDocument(document);
        if (user.isPresent()) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/firstname/{firstname}")
    public ResponseEntity<List<User>> findByFirstName(@PathVariable String firstname) {
        final var list = userService.findByFirstName(firstname);
        if (list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

}
