package com.aportme.backend.component.user.exception;

public class WrongUserCredentialsException extends RuntimeException {

    public WrongUserCredentialsException(){
        super("Email has wrong format or password is shorter than 9 characters");
    }
}
