package org.example.controllers;

import org.example.domain.Todo;
import org.example.domain.plainObject.TodoPojo;
import org.example.services.inrerfaces.IToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping(path = "/user/{userId}/todo")
    public ResponseEntity<TodoPojo> createTodo (@RequestBody Todo todo, @PathVariable Long userId) {
        TodoPojo result = todoService.createTodo(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<TodoPojo> getTodo(@PathVariable Long id, @PathVariable Long userId) {
        return new ResponseEntity<>(todoService.getTodo(id), HttpStatus.OK);
    }

    @GetMapping(path = "/user/{userId}/todos")
    public ResponseEntity<List<TodoPojo>> getAllTodo (@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.getAllTodos(userId), HttpStatus.OK);
    }

    @PutMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<TodoPojo> updateTodo (@RequestBody Todo source, @PathVariable Long id,@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.updateTodo(source, id), HttpStatus.OK);
    }

    @DeleteMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<String> deleteTodo (@PathVariable Long id,@PathVariable Long userId) {
        return new ResponseEntity<>(todoService.deleteTodo(id), HttpStatus.OK);
    }

}