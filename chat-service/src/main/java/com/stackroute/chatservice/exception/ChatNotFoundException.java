package com.stackroute.chatservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ChatNotFoundException extends Exception{
    public ChatNotFoundException(String message) {
        super(message);
    }
}
