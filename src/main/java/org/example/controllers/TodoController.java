package org.example.controllers;

import org.example.domain.Todo;
import org.example.domain.plainObject.TodoPojo;
import org.example.services.inrerfaces.IToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * by Iskortsev S.V.
 */

@RestController
public class TodoController {

    private final IToDoService todoService;

    @Autowired
    public TodoController(IToDoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping(path = "/user/{userId}/todo/create")
    public ResponseEntity<TodoPojo> createTodo(@RequestBody Todo todo, @PathVariable Long userId) {
        TodoPojo result = todoService.createTodo(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}