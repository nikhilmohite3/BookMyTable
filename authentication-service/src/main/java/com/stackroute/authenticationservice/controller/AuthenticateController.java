package com.stackroute.authenticationservice.controller;

import com.stackroute.authenticationservice.entity.AuthRequest;
import com.stackroute.authenticationservice.entity.User;
import com.stackroute.authenticationservice.exception.InvalidTokenException;
import com.stackroute.authenticationservice.exception.UserFoundException;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/login")
public class AuthenticateController
{
	@Autowired
	CustomUserDetailsService userDetailsService;

	@PostConstruct
	public void init()
	{
		userDetailsService.init();
	}
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveData(@RequestBody User user)
	{
		try {
			userDetailsService.saveData(user);
			return new ResponseEntity<>("User Added!!",HttpStatus.OK);
		}
		catch (UserFoundException userFoundException)
		{
			return new ResponseEntity<>("User Already Present!!",HttpStatus.CONFLICT);
		}
	}

	@PutMapping("/updateUser/{emailId}")
	public ResponseEntity<String> updateData(@PathVariable String emailId,@RequestBody User user)
	{
		try
		{
			userDetailsService.updateData(emailId, user);
			return new ResponseEntity<>("User Updated!!",HttpStatus.OK);
		}
		catch (UserNotFoundException userNotFoundException)
		{
			return new ResponseEntity<>("User Not Found!!",HttpStatus.CONFLICT);
		}
	}

	@PostMapping("/Authenticate")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authrequest)
	{
		String Token="";
		try
		{
			Token=userDetailsService.generateToken(authrequest);
			return new ResponseEntity<>(Token,HttpStatus.OK);
		}
		catch(InvalidTokenException ex)
		{
			return new ResponseEntity<>("Invalid Email or password!!",HttpStatus.CONFLICT);
		}
	}
	@GetMapping("/Owner")
	@PreAuthorize("hasAuthority('owner')")
	public  String owner()
	{
		return "URL Accessible to Restaurant Owner";
	}

	@GetMapping("/User")
	@PreAuthorize("hasAuthority('user')")
	public String user()
	{
		return "URL Accessible to User";
	}
}
