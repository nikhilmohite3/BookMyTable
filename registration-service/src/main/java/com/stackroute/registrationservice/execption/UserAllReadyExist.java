package com.stackroute.registrationservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "user already  exist execption", code = HttpStatus.CONFLICT)
public class UserAllReadyExist extends Exception{
}
