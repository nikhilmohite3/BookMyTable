package com.stackroute.tableservice.exception;



//@ResponseStatus(reason = "table space is limited",code = HttpStatus.CONFLICT)
public class TableNumberExceedException extends RuntimeException{
    public TableNumberExceedException() {
        super();
    }
}
