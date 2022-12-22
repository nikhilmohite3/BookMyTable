package com.stackroute.registrationservice.repository;

import com.stackroute.registrationservice.entity.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepo extends MongoRepository<Restaurant, String> {

     List<Restaurant> getAllRestaurantsByRestaurantAddressCity(String city);

     boolean existsByRestaurantAddressCity(String city);
}
