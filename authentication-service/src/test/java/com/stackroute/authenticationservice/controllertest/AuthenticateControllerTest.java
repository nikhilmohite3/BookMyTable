package com.stackroute.authenticationservice.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.authenticationservice.controller.AuthenticateController;
import com.stackroute.authenticationservice.entity.AuthRequest;
import com.stackroute.authenticationservice.entity.Role;
import com.stackroute.authenticationservice.entity.User;
import com.stackroute.authenticationservice.exception.InvalidTokenException;
import com.stackroute.authenticationservice.exception.UserFoundException;
import com.stackroute.authenticationservice.exception.UserNotFoundException;
import com.stackroute.authenticationservice.service.CustomUserDetailsService;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticateControllerTest {

    @Mock
    CustomUserDetailsService service;

    @InjectMocks
    AuthenticateController authenticatecontroller;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authenticatecontroller).build();
    }

    @Test
    void saveDataSuccess() throws Exception {
        User user=new User("jainAman@gmail.com","Aman Jain",Role.user);
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.saveData(user))
                .thenReturn(user);
        mockMvc.perform(post("/login/saveUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("User Added!!")));
    }

    @Test
    void saveDataFailure() throws Exception {
        User user=new User("jainAman@gmail.com","Aman Jain",Role.user);
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.saveData(any()))
                .thenThrow(UserFoundException.class);
        mockMvc.perform(post("/login/saveUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("User Already Present!!")));
    }

    @Test
    void updateDataSuccess() throws Exception {
        User user=new User("jainutkarsh@gmail.com","Utkarsh Jain",Role.user);
        String Email="jainutkarsh@gmail.com";
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.updateData(Email,user))
                .thenReturn(user);
        mockMvc.perform(put("/login/updateUser/jainutkarsh@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("User Updated!!")));
    }

    @Test
    void updateDataFailure() throws Exception {
        User user=new User("jainutkarsh@gmail.com","Utkarsh Jain",Role.user);
        String Email="jainutkarsh@gmail.com";
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.updateData(any(),any()))
                .thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/login/updateUser/jainutkarsh@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("User Not Found!!")));
    }

    @Test
    void generateTokenSuccess() throws Exception {
        AuthRequest authRequest=new AuthRequest("jainutkarsh@gmail.com","utkarsh");
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.generateToken(any()))
                .thenReturn("");
        mockMvc.perform(post("/login/Authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void generateTokenFailure() throws Exception {
        AuthRequest authRequest=new AuthRequest("jainutkarsh@gmail.com","utkarsh");
        ObjectMapper objectmapper=new ObjectMapper();
        when(service.generateToken(any()))
                .thenThrow(InvalidTokenException.class);
        mockMvc.perform(post("/login/Authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectmapper.writeValueAsString(authRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("Invalid Email or password!!")));
    }

    @Test
    void owner() throws Exception {
        mockMvc.perform(get("/login/Owner"))
                .andExpect(content().string(equalTo("URL Accessible to Restaurant Owner")));
    }

    @Test
    void user() throws Exception {
        mockMvc.perform(get("/login/User"))
                .andExpect(content().string(equalTo("URL Accessible to User")));

    }

}