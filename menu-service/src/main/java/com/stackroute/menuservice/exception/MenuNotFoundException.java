package com.stackroute.menuservice.exception;

public class MenuNotFoundException extends RuntimeException{
    private static final  long serialVersionUID=1L;

    public MenuNotFoundException(String msg){
        super(msg);
    }
}