package com.okhrymovych_kalandiak.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

    @GetMapping
    public ResponseEntity<HttpStatus> checkAuth() {
        return new ResponseEntity<HttpStatus>(HttpStatus.OK);
    }

}
