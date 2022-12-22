package com.stackroute.authenticationservice.servicetest;

import com.stackroute.authenticationservice.entity.AuthRequest;
import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.User;
import com.stackroute.authenticationservice.exception.InvalidTokenException;
import com.stackroute.authenticationservice.exception.UserFoundException;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.repository.UserRepository;
import com.stackroute.authenticationservice.service.CustomUserDetailsService;
import com.stackroute.authenticationservice.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService service;

    @Mock
    UserRepository repository;

    @Mock
    JwtUtil jwtutil;

    @Mock
    AuthenticationManager authManager;

    @Test
    void saveDataSuccess() {
    User user=new User("jainAnkit@gmail.com","Ankit",Role.user);
        String Email = "jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.of(new User("jainutkarsh@gmail.com", "utkarsh", Role.user)));
        when(repository.save(user))
                .thenReturn(user);
        assertEquals(user,service.saveData(user));
    }

    @Test
    void saveDataFailure() {
        User user=new User("jainutkarsh@gmail.com", "utkarsh", Role.user);
        String Email = "jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.of(new User("jainutkarsh@gmail.com", "utkarsh", Role.user)));
        assertThrows(UserFoundException.class,()->service.saveData(user));
    }

    @Test
    void updateDataSuccess() {
        User user=new User("jainutkarsh@gmail.com","Utkarsh Jain",Role.user);
        String Email="jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.of(new User("jainutkarsh@gmail.com", "utkarsh", Role.user)));
        when(repository.save(user))
                .thenReturn(user);
        assertEquals(user.getEmail(),service.updateData(Email,user).getEmail());
    }

    @Test
    void updateDataFailure() {
        User user=new User("jainutkarsh@gmail.com","Utkarsh Jain",Role.user);
        String Email = "jain@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->service.updateData(Email,user));
    }

    @Test
    void getDataSuccess() {
        String Email = "jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.of(new User("jainutkarsh@gmail.com", "utkarsh", Role.user)));
        assertEquals(Email, service.getData(Email).getEmail());
    }

    @Test
    void getDataFailure() {
        String Email = "jain@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class,()->service.getData(Email));
    }
    @Test
    void loadUserByUsernameSuccess() {
        String Email = "jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.of(new User("jainutkarsh@gmail.com", "utkarsh", Role.user)));
        assertNotNull(service.loadUserByUsername(Email));
    }

    @Test
    void loadUserByUsernameFailure() {
        String Email = "jainutkarsh@gmail.com";
        when(repository.findById(Email))
                .thenReturn(Optional.empty());
        //when(service.loadUserByUsername(Email)).thenThrow(UserNotFoundException.class);
        assertThrows(UsernameNotFoundException.class,()->service.loadUserByUsername(Email));
    }

    @Test
    void generateTokenSuccess(){
        String token="";
        AuthRequest authRequest=new AuthRequest("jainutkarsh@gmail.com","utkarsh");
        User user=new User("jainutkarsh@gmail.com", "utkarsh", Role.user);
        when(repository.findById(authRequest.getEmail()))
                .thenReturn(Optional.of(user));
        when(jwtutil.generateToken(authRequest.getEmail(),user.getRole()))
                .thenReturn(token);
        assertEquals(token,service.generateToken(authRequest));
    }
    @Test
    void generateTokenFailure(){
        AuthRequest authRequest=new AuthRequest("jainutkarsh@gmail.com","utkarsh");
        when(authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())))
                .thenThrow(InvalidTokenException.class);
        assertThrows(InvalidTokenException.class,()->service.generateToken(authRequest));
      }
}