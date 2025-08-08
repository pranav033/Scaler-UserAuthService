package org.example.userauthservice.controllers;

import org.example.userauthservice.exceptions.PasswordMismatchException;
import org.example.userauthservice.exceptions.UserNotSignedUpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerAdvisor {

    @ExceptionHandler({PasswordMismatchException.class, UserNotSignedUpException.class,UserNotSignedUpException.class})
    public ResponseEntity<String> exceptionHandler(Exception ex)
    {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
