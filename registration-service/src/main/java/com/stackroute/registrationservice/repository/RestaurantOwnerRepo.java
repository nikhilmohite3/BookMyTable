package com.stackroute.registrationservice.repository;

import com.stackroute.registrationservice.entity.RestaurantOwner;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantOwnerRepo extends MongoRepository<RestaurantOwner, String> {

}
