package com.stackroute.registrationservice.ServiceTest;

import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.entity.RestaurantAddress;
import com.stackroute.registrationservice.execption.*;
import com.stackroute.registrationservice.repository.RestaurantRepo;
import com.stackroute.registrationservice.serviceImpl.RestaurantServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    RestaurantRepo restaurantRepo;

    @InjectMocks
    RestaurantServiceImpl restaurantService;

    @Test
    public void registerRestaurant() throws RestaurantAllReadyExist {
        Restaurant restaurant = new Restaurant("101", "restaurant@gmail.com", "restaurant", 9304848904L, new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        when(restaurantService.registerRestaurant(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.registerRestaurant(restaurant));
    }

    @Test
    public  void registerRestaurantFail()
    {
        Restaurant restaurant = new Restaurant("101", "restaurant@gmail.com", "restaurant", 9304848904L, new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        String restaurantId ="101";
        when(restaurantRepo.existsById(restaurantId)).thenReturn(true);
        assertThrows(RestaurantAllReadyExist.class,()->restaurantService.registerRestaurant(restaurant));

    }

    @Test
    public void updateRestaurant() throws RestaurantNotFoundException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setRestaurantName("rest");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setMobNo(9304848904L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        when(restaurantRepo.findById(restaurant.getRestaurantId())).thenReturn(Optional.of(restaurant));
        when(restaurantRepo.save(restaurant)).thenReturn(restaurant);
        assertEquals(restaurant, restaurantService.updateRestaurant(restaurant));
    }

    @Test
    public void updateRestaurantFail()
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setRestaurantName("rest");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setMobNo(7761907233L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        when(restaurantRepo.findById(restaurant.getRestaurantId()))
                .thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class,()->restaurantService.updateRestaurant(restaurant));
    }

    @Test
    public void deleteRestaurant() throws RestaurantNotFoundException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setRestaurantName("rest");
        restaurant.setMobNo(9304848904L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));

        when(restaurantRepo.existsById(restaurant.getRestaurantId())).thenReturn(true);
        restaurantRepo.deleteById(restaurant.getRestaurantId());
        verify(restaurantRepo, times(1)).deleteById(restaurant.getRestaurantId());
        assertEquals("deleted successfully", restaurantService.deleteRestaurant(restaurant.getRestaurantId()));


    }

    @Test
    public void deleteRestaurantFail()
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setRestaurantName("rest");
        restaurant.setMobNo(9304848904L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        String restaurantId = "Res123";
        restaurantRepo.deleteById(restaurant.getEmailId());
        assertThrows(RestaurantNotFoundException.class,()->restaurantService.deleteRestaurant(restaurant.getRestaurantId()));
    }

    @Test
    public void getaAllRestaurant() throws CityNotFoundException {
        Restaurant restaurant = new Restaurant();
        RestaurantAddress restaurantAddress = new RestaurantAddress();
        restaurant.setRestaurantId("Res123");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setRestaurantName("rest");
        restaurant.setMobNo(9304848904L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        List<Restaurant> restaurants =  new ArrayList<>();
        restaurants.add(restaurant);
         String city = "siwan";
        when(restaurantRepo.existsByRestaurantAddressCity(city)).thenReturn(true);
        when(restaurantRepo.getAllRestaurantsByRestaurantAddressCity(city)).thenReturn(restaurants);
        assertEquals(restaurants, restaurantService.getAllRestaurant(city));



    }
    @Test
    public void getAllRestaurantFail()
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setRestaurantName("rest");
        restaurant.setMobNo(9304848904L);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar", 841406));
        List<Restaurant> restaurants =  new ArrayList<>();
        restaurants.add(restaurant);
        String city = "siwan";
        restaurantRepo.getAllRestaurantsByRestaurantAddressCity(city);
        assertThrows(CityNotFoundException.class,()->restaurantService.getAllRestaurant(city));


    }



}
