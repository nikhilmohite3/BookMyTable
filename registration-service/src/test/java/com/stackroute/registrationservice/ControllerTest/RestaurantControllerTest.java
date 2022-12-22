package com.stackroute.registrationservice.ControllerTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.registrationservice.controller.RestaurantController;
import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.entity.RestaurantAddress;
import com.stackroute.registrationservice.execption.*;
import com.stackroute.registrationservice.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    public void setUp()
    {

        mockMvc = MockMvcBuilders.standaloneSetup(restaurantController).build();


    }

    @Test
    public void registerRestaurant() throws Exception
    {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.registerRestaurant(restaurant)).thenReturn(restaurant);

        mockMvc.perform(post("/api/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("Restaurant Registered")))
                .andExpect(status().isCreated());

    }

    @Test
    public void registerRestaurantFail() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.registerRestaurant(any())).thenThrow(RestaurantAllReadyExist.class);
        mockMvc.perform(post("/api/v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("Restaurant all ready exist")));


    }

    @Test
    public void registerRestaurantFailed() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.registerRestaurant(any())).thenThrow(new RuntimeException());
        mockMvc.perform(post("/api/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("Restaurant can't be null")));
    }
    @Test
    public void updateRestaurant() throws  Exception
    {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.updateRestaurant(restaurant)).thenReturn(restaurant);
        mockMvc.perform(put("/api/v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("updated successfully")));
    }

    @Test
    public void updateRestaurantFail() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.updateRestaurant(any())).thenThrow(RestaurantNotFoundException.class);
        mockMvc.perform(put("/api/v2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("restaurant doesn't exist")));

    }

    @Test
    public void updateRestaurantFailed() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9833233, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.updateRestaurant(any())).thenThrow(new RuntimeException());
        mockMvc.perform(put("/api/v2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("something went wrong")));
    }
    @Test
    public void deleteRestaurant() throws Exception
    {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9304848904L, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        String restaurantId ="Rest123";
        when(restaurantService.deleteRestaurant(restaurantId)).thenReturn(String.valueOf(restaurant));
        mockMvc.perform(delete("/api/v2/Rest122")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(equalTo("Restaurant deleted successfully")));

    }

    @Test
    public void deleteRestaurantFail() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9304848904L, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        String restaurantId ="Rest122";
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.deleteRestaurant(restaurantId)).thenThrow(RestaurantNotFoundException.class);
        mockMvc.perform(delete("/api/v2/Rest122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("restaurant doesn't exist")));
    }

    @Test
    public void deleteRestaurantFailed() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 9304848904L, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        String restaurantId ="Rest122";
        ObjectMapper objectMapper = new ObjectMapper();
        when(restaurantService.deleteRestaurant(restaurantId)).thenThrow(new RuntimeException());
        mockMvc.perform(delete("/api/v2/Rest122")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(equalTo("something went Wrong")));

    }

    @Test
    public void getAllRestaurant() throws Exception
    {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 93048484904L, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        String city = "siwan";
        when(restaurantService.getAllRestaurant(city)).thenReturn(List.of(restaurant));
        mockMvc.perform(get("/api/v2/siwan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void getAllRestaurantFail() throws Exception {
        Restaurant restaurant = new Restaurant("Rest122", "restaurant@gmail.com", "rest", 93048484904L, new RestaurantAddress("103","marar","siwan","basantpur","ma kali", "bihar", 841406));
        ObjectMapper objectMapper = new ObjectMapper();
        String city = "siwan";
        when(restaurantService.getAllRestaurant(any())).thenThrow(CityNotFoundException.class);
        mockMvc.perform(get("/api/v2/siwan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(restaurant)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(content().string(equalTo("not found")));

    }


}
