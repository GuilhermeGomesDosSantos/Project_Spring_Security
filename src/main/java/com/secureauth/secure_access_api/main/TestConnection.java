package com.secureauth.secure_access_api.main;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class TestConnection {
    @GetMapping("/test")
    public ResponseEntity <String> testConnection(){
        String message = "Hello World!";
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
