package com.stackroute.registrationservice.ServiceTest;

import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.UserAllReadyExist;
import com.stackroute.registrationservice.execption.UserNotFoundException;
import com.stackroute.registrationservice.repository.UserRepo;
import com.stackroute.registrationservice.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

   @Mock
    UserRepo userRepo;
    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void userRegisterTest() throws UserAllReadyExist {
        User user = new User("singhshubham@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        when(userService.registerUser(user)).thenReturn(user);
        assertEquals(user, userService.registerUser(user));



    }

    @Test
    public void userRegisterFailTest() throws UserAllReadyExist {
        User user = new User("singhshubham@gmail.com", "shubham", "singh", 930484890, "siwan", 25);
        String email ="singhshubham@gmail.com";
        when(userRepo.existsById(email)).thenReturn(true);
        assertThrows(UserAllReadyExist.class,()->userService.registerUser(user));


    }

    @Test
    public void updateUser() throws UserNotFoundException {
        User user = new User();
        user.setEmailId("singh@gmail.com");
        user.setFirstName("shubham");
        user.setLastName("khushi");
        user.setMobNo(8002970262L);
        user.setAddress("pakadi");
        user.setAge(30);
        when(userRepo.findById(user.getEmailId())).thenReturn(Optional.of(user));
        when(userRepo.save(user)).thenReturn(user);
        assertEquals(user, userService.updateUser(user, user.getEmailId()));


    }

    @Test
    public void updateUserFail()
    {
        User user=new User("singhshubham@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        String email = "singhshubham@gmail.com";
        when(userRepo.findById(email))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->userService.updateUser(user, email));
    }

    @Test
    public void deleteUserTest() throws UserAllReadyExist, UserNotFoundException {
        User user = new User();
        user.setEmailId("singh@gmail.com");
        user.setFirstName("shubham");
        user.setLastName("khushi");
        user.setMobNo(7761907233L);
        user.setAddress("pakadi");
        user.setAge(30);
        when(userRepo.existsById(user.getEmailId())).thenReturn(true);
        userRepo.deleteById(user.getEmailId());
        verify(userRepo,times(1)).deleteById(user.getEmailId());
        assertEquals("deleted successfully", userService.deleteUser(user.getEmailId()));

    }
    @Test
    public void deleteUserFail()
    {
        User user = new User();
        user.setEmailId("singh@gmail.com");
        user.setFirstName("shubham");
        user.setLastName("khushi");
        user.setMobNo(7761907233L);
        user.setAddress("pakadi");
        user.setAge(30);
        String email ="singh@gmail.com";
        userRepo.deleteById(user.getEmailId());
        assertThrows(UserNotFoundException.class,()->userService.deleteUser(user.getEmailId()));

    }





}
