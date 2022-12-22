package com.stackroute.registrationservice.ControllerTest;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.registrationservice.controller.UserController;
import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.UserAllReadyExist;
import com.stackroute.registrationservice.execption.UserNotFoundException;
import com.stackroute.registrationservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



//@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {


    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;
    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();


    }

    @Test
    public void registerUser() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        ObjectMapper objectMapper = new ObjectMapper();
     when(userService.registerUser(user))
               .thenReturn(user);
            mockMvc.perform(post("/api/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user))).andDo(print())
                    .andExpect(content().string(equalTo("User Register Successfully")))
                    .andExpect(status().isCreated());

    }

    @Test
    public void registerUserFail() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        ObjectMapper objectMapper = new ObjectMapper();
        when(userService.registerUser(any()))
                .thenThrow(UserAllReadyExist.class);
        mockMvc.perform(post("/api/v3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("user already exits")));


    }

    @Test
    public void registerUserFailed() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        ObjectMapper objectMapper = new ObjectMapper();
        when(userService.registerUser(any()))
                .thenThrow(new Exception());
        mockMvc.perform(post("/api/v3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("User can't be null")));
    }

    @Test
    public void updateUser() throws Exception
    {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        String emailId ="rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        when(userService.updateUser(user, emailId)).thenReturn(user);
        mockMvc.perform(put("/api/v3/rupam123@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("updated successfully")));

    }

    @Test
    public void updateUserFail() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        ObjectMapper objectMapper = new ObjectMapper();
        String emailId = "rupam123@gmail.com";
        when(userService.updateUser(any(), any())).thenThrow(UserNotFoundException.class);
        mockMvc.perform(put("/api/v3/rupam123@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("user doesn't exist")));
    }

    @Test
    public void updateUserFailed() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        ObjectMapper objectMapper = new ObjectMapper();
        String emilId ="rupam123@gmail.com";
        when(userService.updateUser(any(),any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/v3/rupam123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("something went wrong")));

    }

    @Test
    public void deleteUser() throws Exception
    {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        String emailId = "rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(delete("/api/v3/rupam123@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("User deleted successfully")));
    }

    @Test
    public void deleteUserFail() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        String emailId = "rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        when(userService.deleteUser(emailId)).thenThrow(UserNotFoundException.class);
        mockMvc.perform(delete("/api/v3/rupam123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("user not Exist")));

    }

    @Test
    public void deleteUserFailed() throws Exception {
        User user = new User("rupam123@gmail.com", "shubham", "singh", 9304848904L, "siwan", 25);
        String emailId = "rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        when(userService.deleteUser(emailId))
                .thenThrow(new RuntimeException());
        mockMvc.perform(delete("/api/v3/rupam123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("something went Wrong")));

    }


}
