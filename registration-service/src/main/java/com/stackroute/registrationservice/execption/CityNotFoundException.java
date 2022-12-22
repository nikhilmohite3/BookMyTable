package com.stackroute.registrationservice.execption;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "City doestNot match", code = HttpStatus.CONFLICT)
public class CityNotFoundException extends  Exception{
}
