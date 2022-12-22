package com.stackroute.authenticationservice.repository;

import com.stackroute.authenticationservice.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, String>{
}
