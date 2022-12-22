package com.stackroute.registrationservice.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document
public class User {
	
	@Id
	@NotNull
	@Email
	private String emailId;
	@NotNull
	@Max(10)
	private String firstName;
	@NotNull
	@Max(10)
	private String lastName;
	@NotNull
	@Size(min=10,max=10)
	private long mobNo ;
	@Max(200)
	private String address;
	@Max(150)
	private int age ;


}
