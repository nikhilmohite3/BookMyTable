package com.stackroute.authenticationservice.exception;

public class UserFoundException extends RuntimeException {

    public UserFoundException(String msg)
    {
        super(msg);
    }
}
