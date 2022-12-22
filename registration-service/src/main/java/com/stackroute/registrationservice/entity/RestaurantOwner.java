package com.stackroute.registrationservice.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Setter
@Getter
public class RestaurantOwner {
	
	@Id
	@NotNull
	@Email
	private String emailId;

	@NotNull
	@Max(20)
	private String firstName ;

	@NotNull
	@Max(10)
	private String lastName;

	@Size(min=0,max=10)
	private long mobNo;


	@NotNull
	private  String address;

}
