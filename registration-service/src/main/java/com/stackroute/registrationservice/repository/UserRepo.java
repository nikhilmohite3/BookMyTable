package com.stackroute.registrationservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stackroute.registrationservice.entity.User;

public interface UserRepo extends MongoRepository<User, String>{
	

}
