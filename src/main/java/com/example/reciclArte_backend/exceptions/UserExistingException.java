package com.example.reciclArte_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistingException extends RuntimeException {
    public UserExistingException(String message) {
        super(message);
    }
}
