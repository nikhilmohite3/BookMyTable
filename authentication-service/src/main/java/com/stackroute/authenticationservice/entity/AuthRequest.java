package com.stackroute.authenticationservice.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest {
	String email;
	String password;
}
