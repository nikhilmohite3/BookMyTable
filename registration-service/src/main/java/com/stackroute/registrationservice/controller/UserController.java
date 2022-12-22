package com.stackroute.registrationservice.controller;

import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.UserAllReadyExist;

import com.stackroute.registrationservice.execption.UserNotFoundException;
import com.stackroute.registrationservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v3")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping
    public ResponseEntity<String> RegisterUser(@RequestBody User user) throws UserAllReadyExist {

        try {
            log.info("User Registered");
            userService.registerUser(user);
            return new  ResponseEntity<>("User Register Successfully", HttpStatus.CREATED);
        }catch (UserAllReadyExist e)
        {
            log.error("User can't Register");
//            e.printStackTrace();
            return  new ResponseEntity<>("user already exits", HttpStatus.CONFLICT);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("User can't be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{emailId}")
    public ResponseEntity<String > updateUser(@RequestBody User user, @PathVariable String emailId) throws UserNotFoundException {
        try {
                log.info("updating user");
                userService.updateUser(user, emailId);
            return ResponseEntity.ok("updated successfully");
        }catch( UserNotFoundException e)
        {
            log.error("user doesn't exits");
            e.printStackTrace();
            return new ResponseEntity<>("user doesn't exist", HttpStatus.CONFLICT);
        }
        catch (RuntimeException e)
        {
            log.error("Something went wrong");
            return new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{emailId}")
    public ResponseEntity<String> deleteUser(@PathVariable String emailId)
    {
        try {
            log.info("User deleted");
            userService.deleteUser(emailId);
            return ResponseEntity.ok("User deleted successfully");
        }catch (UserNotFoundException e)
        {
            log.error("User doesn't exits");
            e.printStackTrace();
            return new ResponseEntity<>("user not Exist", HttpStatus.CONFLICT);
        }
        catch (RuntimeException e)
        {
            log.error("Something went wrong");
            return new ResponseEntity<>("something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
