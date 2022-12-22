package com.stackroute.registrationservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Owner All Ready exist execption", code = HttpStatus.CONFLICT)
public class OwnerAllReadyExist extends Exception{
}
