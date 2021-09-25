package org.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * by Iskortsev S.V.
 */

@Controller
public class TestController {

    @GetMapping(path = "/test")
    public ResponseEntity<String> getTest() {
        return new ResponseEntity<>("This is our test message", HttpStatus.OK);
    }
}
