package com.stackroute.tableservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "dont find any restaurant", code = HttpStatus.CONFLICT)
public class RestaurantNotFoundException extends Exception{
    public RestaurantNotFoundException() {
        super();
    }
}
