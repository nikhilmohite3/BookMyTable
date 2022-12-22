package com.stackroute.authenticationservice.service;

import java.util.*;

import com.stackroute.authenticationservice.entity.AuthRequest;
import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.User;
import com.stackroute.authenticationservice.exception.InvalidTokenException;
import com.stackroute.authenticationservice.exception.UserFoundException;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.authenticationservice.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Here we are validating as giving username and password to User spring security to validate the user details from the database.
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	UserRepository repository;

	@Autowired
	JwtUtil jwtutil;

	@Autowired
	AuthenticationManager authManager;

	@Override
	public UserDetails loadUserByUsername(String Email) throws UsernameNotFoundException {
		Optional<User> user=repository.findById(Email);
		if(user.isPresent())
		{
			CustomUserDetails userDetails=new CustomUserDetails(user.get());
			return userDetails;
		}
		else {
			log.info("Inside the else part in CustomUserDetailsService when user is not found in MYSQL-database");
			throw new UsernameNotFoundException("User Not Found");
		}
	}

	public User saveData(User user)
	{
		Optional<User> users=repository.findById(user.getEmail());
		if(users.isPresent())
		{
			throw new UserFoundException("User Already Present!!");
		}
		else
		{
			return repository.save(user);
		}
	}
	public User updateData(String emailId,User user) throws UserNotFoundException
	{
		Optional<User> user1= repository.findById(emailId);
		if(user1.isPresent())
		{
			user.setEmail(user1.get().getEmail());
			return repository.save(user);
		}
		else
		{
			throw new UserNotFoundException("User Not Found!!");
		}
	}
	public User getData(String emailId)
	{
		Optional<User> user1= repository.findById(emailId);
		if(user1.isPresent())
		{
			return user1.get();
		}
		else
		{
			throw new UserNotFoundException("User Not Found!!");
		}
	}
	public String generateToken(AuthRequest authrequest){
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getEmail(), authrequest.getPassword()));
			return jwtutil.generateToken(authrequest.getEmail(),getData(authrequest.getEmail()).getRole());
		}
		catch(Exception exception)
		{
			log.error("Invalid Email or password!!");
		}
		throw new InvalidTokenException("Invalid Email or password!!");
	}
	public void init()
	{
		User user=new User("jainHarsh@gmail.com","Harsh", Role.user);
		repository.save(user);
	}
}