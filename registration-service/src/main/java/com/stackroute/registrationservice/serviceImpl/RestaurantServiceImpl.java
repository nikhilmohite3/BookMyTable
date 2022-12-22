package com.stackroute.registrationservice.serviceImpl;
import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.execption.*;
import com.stackroute.registrationservice.repository.RestaurantRepo;
import com.stackroute.registrationservice.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class RestaurantServiceImpl implements RestaurantService {

    // As a owner i should be able to add restaurant so that user can see the restaurant
    // when owner want to add restaurant so owner can easily register
    @Autowired
    private RestaurantRepo restaurantRepo;
    @Override
    public Restaurant registerRestaurant(Restaurant restaurant) throws RestaurantAllReadyExist {

        if (restaurantRepo.existsById(restaurant.getRestaurantId()))
        {
            throw new RestaurantAllReadyExist();
        }

       Restaurant restaurant1 =  restaurantRepo.save(restaurant);
        return restaurant1;
    }

        //As a owner i should be able to edit my restaurant details so that i can update them

    @Override
    public Restaurant updateRestaurant(Restaurant restaurant) throws RestaurantNotFoundException {

        Optional<Restaurant> restaurant1 = Optional.ofNullable(restaurantRepo.findById(restaurant.getRestaurantId()).orElseThrow(() -> new RestaurantNotFoundException()));
        restaurant.setEmailId(restaurant1.get().getEmailId());
        return restaurantRepo.save(restaurant);
    }

    // RestaurantOwner want to delete restaurant profile than owner can easily delete
    @Override
    public  String deleteRestaurant(String restaurantId) throws RestaurantNotFoundException {
        if(restaurantRepo.existsById(restaurantId))
        {
            restaurantRepo.deleteById(restaurantId);
            return "deleted successfully";

        }
        throw new RestaurantNotFoundException();

    }

    @Override
    public List<Restaurant> getAllRestaurant(String city) throws CityNotFoundException {
            if(restaurantRepo.existsByRestaurantAddressCity(city)) {
                return restaurantRepo.getAllRestaurantsByRestaurantAddressCity(city);
            }
            throw new CityNotFoundException();
    }
}
