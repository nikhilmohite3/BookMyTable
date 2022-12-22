package com.stackroute.registrationservice.service;
import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.UserNotFoundException;


public interface UserService {
	
	User updateUser(User user, String emailId) throws UserNotFoundException;

	 User registerUser(User user) throws Exception;

	 String deleteUser(String restaurantId) throws UserNotFoundException;
	
	
	

}
