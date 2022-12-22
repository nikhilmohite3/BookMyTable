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
public class Restaurant {
	@Id
	private String restaurantId;
	@Email
	private String emailId;
	@NotNull
	@Max(200)
	private String restaurantName;
	@NotNull
	@Size(min=0,max=10)
	private long mobNo;
	@NotNull
	private RestaurantAddress restaurantAddress;


}