package com.stackroute.authenticationservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="User")
public class User {
	@Id
	String email;
	String password;
	@Enumerated(EnumType.STRING)
	Role role;
}
