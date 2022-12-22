package com.stackroute.registrationservice.service;

import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.execption.*;

import java.util.List;

public interface RestaurantService {

     Restaurant registerRestaurant(Restaurant restaurant) throws RestaurantAllReadyExist;

    Restaurant updateRestaurant(Restaurant restaurant) throws RestaurantNotFoundException, OwnerNotFoundException;

    String deleteRestaurant(String restaurantId) throws RestaurantNotFoundException;

   List<Restaurant> getAllRestaurant(String city) throws CityNotFoundException;
}
