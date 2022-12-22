package com.stackroute.registrationservice.execption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Restaurant All ready  exist execption", code = HttpStatus.CONFLICT)
public class RestaurantAllReadyExist extends Exception{
}
