package com.aportme.aportme.backend.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason="User with this email already exists.")
public class UserAlreadyExistsException extends RuntimeException {}
