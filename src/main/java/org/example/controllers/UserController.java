package org.example.controllers;

import org.example.domain.ToDo;
import org.example.domain.User;
import org.example.services.inrerfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * by Iskortsev S.V.
 */

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path = "/todo")
    public ResponseEntity<ToDo> createToDo(@RequestBody ToDo todo) {
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }
}
