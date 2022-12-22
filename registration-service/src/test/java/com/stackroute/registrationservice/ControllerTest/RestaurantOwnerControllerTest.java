package com.stackroute.registrationservice.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.registrationservice.controller.RestaurantOwnerController;
import com.stackroute.registrationservice.entity.RestaurantOwner;
import com.stackroute.registrationservice.execption.OwnerAllReadyExist;
import com.stackroute.registrationservice.execption.OwnerNotFoundException;
import com.stackroute.registrationservice.execption.UserNotFoundException;
import com.stackroute.registrationservice.service.RestaurantOwnerService;
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

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantOwnerControllerTest {


    @Mock
    private RestaurantOwnerService restaurantOwnerService;

    @InjectMocks
    private RestaurantOwnerController restaurantOwnerController;

    @Autowired
    MockMvc mockMvc;
    @BeforeEach
    public void setUp()
    {

        mockMvc = MockMvcBuilders.standaloneSetup(restaurantOwnerController).build();


    }

    @Test
    public void registerRestaurantOwner() throws  Exception
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        ObjectMapper objectMapper =new ObjectMapper();
        when(restaurantOwnerService.registerOwner(restaurantOwner)).thenReturn(restaurantOwner);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(content().string(equalTo("RestaurantOwner Register")))
              .andExpect(status().isCreated());


    }

    @Test
    public void registerRestaurantOwnerFail() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9404848904L, "siwan");
        ObjectMapper objectMapper =  new ObjectMapper();
        when(restaurantOwnerService.registerOwner(any())).thenThrow(OwnerAllReadyExist.class);
        mockMvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("Owner all ready Exist")));
    }

    @Test
    public void registerRestaurantOwnerFailed() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9404848904L, "siwan");
        ObjectMapper objectMapper =  new ObjectMapper();
        when(restaurantOwnerService.registerOwner(any()))
                .thenThrow(new RuntimeException());
        mockMvc.perform(post("/api/v1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("Owner can't be null")));

    }

    @Test
    public void updateRestaurantOwner() throws Exception
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        ObjectMapper objectMapper = new ObjectMapper();
        String emailId= "owner@gmail.com";
        when(restaurantOwnerService.updateOwner(restaurantOwner,emailId)).thenReturn(restaurantOwner);
        mockMvc.perform(put("/api/v1/owner@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Updated successfully")));

    }

    @Test
    public void updateRestaurantOwnerFail() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        ObjectMapper objectMapper = new ObjectMapper();
        String email="owner@gmail.com";
        when(restaurantOwnerService.updateOwner(any(),any())).thenThrow(OwnerNotFoundException.class);
        mockMvc.perform(put("/api/v1/owner@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("RestaurantOwner doesn't exist")));


    }

    @Test
    public void updateRestaurantOwnerFailed() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        ObjectMapper objectMapper = new ObjectMapper();
        String email="owner@gmail.com";
        when(restaurantOwnerService.updateOwner(any(),any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/v1/owner@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("Something  went wording")));

    }

    @Test
    public void deleteRestaurantOwner() throws  Exception
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 93048480904L, "siwan");
        ObjectMapper objectMapper = new ObjectMapper();
        String emailId ="owner@gmail.com";
        when(restaurantOwnerService.deleteOwner(emailId)).thenReturn(String.valueOf(restaurantOwner));
        mockMvc.perform(delete("/api/v1/owner@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Owner Delete successfully")));
    }

    @Test
    public void deleteRestaurantOwnerFail() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        String emailId = "rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantOwnerService.deleteOwner(emailId)).thenThrow(OwnerNotFoundException.class);
        mockMvc.perform(delete("/api/v1/rupam123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("owner doesn't exist")));

    }

    @Test
    public void deleteRestaurantOwnerFailed() throws Exception {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "owner", "singh", 9304848904L, "siwan");
        String emailId = "rupam123@gmail.com";
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantOwnerService.deleteOwner(emailId)).thenThrow(new RuntimeException());
        mockMvc.perform(delete("/api/v1/rupam123@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurantOwner)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("something went wrong")));

    }

}