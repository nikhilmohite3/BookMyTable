package com.stackroute.registrationservice.service;

import com.stackroute.registrationservice.entity.RestaurantOwner;
import com.stackroute.registrationservice.execption.OwnerAllReadyExist;
import com.stackroute.registrationservice.execption.OwnerNotFoundException;

public interface RestaurantOwnerService {


    RestaurantOwner registerOwner(RestaurantOwner restaurantOwner) throws OwnerAllReadyExist;

    RestaurantOwner updateOwner(RestaurantOwner restaurantOwner, String emailId) throws OwnerNotFoundException;

    String deleteOwner(String emailId) throws OwnerNotFoundException, OwnerAllReadyExist;

}
