package com.stackroute.registrationservice.serviceImpl;

import com.stackroute.registrationservice.entity.RestaurantOwner;
import com.stackroute.registrationservice.execption.OwnerAllReadyExist;
import com.stackroute.registrationservice.execption.OwnerNotFoundException;
import com.stackroute.registrationservice.repository.RestaurantOwnerRepo;
import com.stackroute.registrationservice.service.RestaurantOwnerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantOwnerServiceImpl implements RestaurantOwnerService {
    @Autowired
    private RestaurantOwnerRepo restaurantOwnerRepo;

//As a owner, i should be able to register so that i can have an account with the system
    @Override
    public RestaurantOwner registerOwner(RestaurantOwner restaurantOwner) throws OwnerAllReadyExist {
        if (restaurantOwnerRepo.existsById(restaurantOwner.getEmailId()))
        {
            throw new OwnerAllReadyExist();
        }
        RestaurantOwner registerOwner = this.restaurantOwnerRepo.save(restaurantOwner);
        return registerOwner ;

    }
    //As a owner i should be able to edit my profile details so that i can update them
    @Override
    public RestaurantOwner updateOwner(RestaurantOwner restaurantOwner, String emailId) throws  OwnerNotFoundException {


        Optional<RestaurantOwner> owner = (Optional<RestaurantOwner>) Optional.ofNullable(this.restaurantOwnerRepo.findById(emailId).orElseThrow(() -> new OwnerNotFoundException()));
            restaurantOwner.setEmailId(owner.get().getEmailId());
        return restaurantOwnerRepo.save(restaurantOwner);
    }

    // when owner want to delete profile than easily can delete
    @Override
    public String deleteOwner(String emailId) throws OwnerNotFoundException {
        if (restaurantOwnerRepo.existsById(emailId))
        {

            restaurantOwnerRepo.deleteById(emailId);
            return "deleted successfully";

        }
        throw new OwnerNotFoundException();
    }


}

