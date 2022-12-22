package com.stackroute.registrationservice.RepoTest;
import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.entity.RestaurantAddress;
import com.stackroute.registrationservice.repository.RestaurantRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;



@AutoConfigureMockMvc
@DataMongoTest
public class RestaurantRepoTest {


    @Autowired
    private RestaurantRepo restaurantRepo;

    @Test
    public void getAllRestaurant()
    {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId("Res123");
        restaurant.setRestaurantName("rest");
        restaurant.setEmailId("rest@gmail.com");
        restaurant.setMobNo(59595959);
        restaurant.setRestaurantAddress(new RestaurantAddress("103", "marar", "siwan", "basantpur", "ma kali", "bihar",841406));

        List<Restaurant> restaurants =  new ArrayList<>();

        restaurants.add(restaurant);
        String city = "siwan";
       List<Restaurant> restaurants1 = restaurantRepo.getAllRestaurantsByRestaurantAddressCity(city);
       assertThat(restaurants1).isNotNull();









    }



}