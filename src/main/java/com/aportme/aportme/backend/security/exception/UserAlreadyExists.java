package com.aportme.aportme.backend.security.exception;

import lombok.AllArgsConstructor;
import org.springframework.security.core.AuthenticationException;


public class UserAlreadyExists extends AuthenticationException {

    public UserAlreadyExists(String msg) {
        super(msg);
    }
}
