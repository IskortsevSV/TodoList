package org.example.controllers;

import org.example.domain.User;
import org.example.domain.plainObject.UserPojo;
import org.example.exceptions.CustomEmptyDataException;
import org.example.services.inrerfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * Exception handling
     * */

    //конфликт имен
    @ExceptionHandler
    public ResponseEntity<String> onConflictUsername(DataIntegrityViolationException exception) {
        //можно вписать в систему логирования
        return ResponseEntity.status(HttpStatus.CONFLICT) // в теле получим имя класса
                .body(ClassUtils.getShortName(exception.getClass()) + ": User with such name already registered");
    }
    //попытка обратится к несуществующему пользователю
    @ExceptionHandler
    public ResponseEntity<String> onMissingUserId(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass()) + ": No such user was found");
    }

    //ошибка доступа к пустой базе
    @ExceptionHandler
    public ResponseEntity<String> onMissingUser (EmptyResultDataAccessException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                + exception.getLocalizedMessage()
                + ": no one user was found");
    }

    @ExceptionHandler
    public ResponseEntity<String> SQLProblems (SQLException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ClassUtils.getShortName(exception.getClass())
                + exception.getSQLState()
                + exception.getLocalizedMessage()
                + ": something went wrong with user");
    }

    @ExceptionHandler
    public ResponseEntity<String> customExceptionHandler (CustomEmptyDataException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ClassUtils.getShortName(exception.getClass())
                + " "
                + exception.getCause()
                + " "
                + exception.getLocalizedMessage());
    }

}
