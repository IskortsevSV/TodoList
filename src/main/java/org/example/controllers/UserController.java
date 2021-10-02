package org.example.controllers;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import org.example.services.inrerfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * by Iskortsev S.V.
 */

@RestController
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<UserPojo> createUser(@RequestBody User user) {
        UserPojo result = userService.createUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/user/{id}")
    public ResponseEntity<UserPojo> getUser(@PathVariable Long id) {
        UserPojo result = userService.getUser(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(path = "/users")
    public ResponseEntity<List<UserPojo>> getUser() {
        List<UserPojo> result = userService.getAllUsers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping(path = "/user/{id}")
    public ResponseEntity<UserPojo> updateUser(@RequestBody User source, @PathVariable Long id) {
        UserPojo result = userService.updateUser(source, id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{id}")
    public ResponseEntity<String> deleteUser (@PathVariable Long id) {
        return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
    }

}
