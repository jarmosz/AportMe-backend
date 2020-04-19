package com.aportme.aportme.backend.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason="User password should be greater than 8 and less than 256 characters.")
public class BadUserPasswordException extends RuntimeException {}
