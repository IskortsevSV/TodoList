package org.example.controllers;

import org.example.annotations.Authentication;
import org.example.domain.Todo;
import org.example.domain.plainObject.TodoPojo;
import org.example.exceptions.CustomEmptyDataException;
import org.example.services.inrerfaces.IToDoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * by Iskortsev S.V.
 */

@RestController
public class TodoController {

    private final IToDoService todoService;
    private Long userId;

    @Autowired
    public TodoController(IToDoService todoService) {
        this.todoService = todoService;
    }

    @Authentication
    @PostMapping(path = "/user/{userId}/todo")
    public ResponseEntity<TodoPojo> createTodo (HttpServletRequest request, @RequestBody Todo todo) {
        TodoPojo result = todoService.createTodo(todo, userId);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
    @Authentication
    @GetMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<TodoPojo> getTodo (HttpServletRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(todoService.getTodo(id, userId), HttpStatus.OK);
    }
    @Authentication
    @GetMapping(path = "/user/{userId}/todos")
    public ResponseEntity<List<TodoPojo>> getAllTodo (HttpServletRequest request) {
        return new ResponseEntity<>(todoService.getAllTodos(userId), HttpStatus.OK);
    }
    @Authentication
    @PutMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<TodoPojo> updateTodo (HttpServletRequest request, @RequestBody Todo source, @PathVariable Long id) {
        return new ResponseEntity<>(todoService.updateTodo(source, id, userId), HttpStatus.OK);
    }
    @Authentication
    @DeleteMapping(path = "/user/{userId}/todo/{id}")
    public ResponseEntity<String> deleteTodo (HttpServletRequest request, @PathVariable Long id) {
        return new ResponseEntity<>(todoService.deleteTodo(id, userId), HttpStatus.OK);
    }

    /**
     * Exception handling
     */

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodoName (DataIntegrityViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass()) + ": Name of todo is obligatory");
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodoId(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                + " # "
                + exception.getLocalizedMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> onMissingTodo (EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass()) + ": no one todo was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> SQLProblems (SQLException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(ClassUtils.getShortName(exception.getClass())
                + exception.getSQLState()
                + exception.getLocalizedMessage()
                + ": something went wrong with todo");
    }

    @ExceptionHandler
    public ResponseEntity<String> customExceptionHandler (CustomEmptyDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ClassUtils.getShortName(exception.getClass())
                + " "
                + exception.getCause()
                + " "
                + exception.getLocalizedMessage());
    }

}