package com.stackroute.registrationservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "user doesn't exist execption", code = HttpStatus.CONFLICT)
public class UserNotFoundException extends Exception{


//    private String sms;
//
//    public UserNotFoundExecption(String sms)
//    {
//        this.sms =sms;
//
//
//    }
}
