package com.stackroute.registrationservice.controller;

import com.stackroute.registrationservice.entity.Restaurant;
import com.stackroute.registrationservice.execption.CityNotFoundException;
import com.stackroute.registrationservice.execption.RestaurantAllReadyExist;
import com.stackroute.registrationservice.execption.RestaurantNotFoundException;
import com.stackroute.registrationservice.service.RestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v2")
@CrossOrigin
public class RestaurantController {
    private RestaurantService restaurantService;

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping
    public ResponseEntity<String> registerRestaurant(@RequestBody Restaurant restaurant) throws RestaurantAllReadyExist {
        try {
                log.info("Registered Restaurant");
            restaurantService.registerRestaurant(restaurant);
            return  new ResponseEntity<>("Restaurant Registered", HttpStatus.CREATED);
        }catch (RestaurantAllReadyExist e)
        {
            log.error("Restaurant all ready exist");
            e.printStackTrace();
            return  new ResponseEntity<>("Restaurant all ready exist", HttpStatus.CONFLICT);
        }catch (RuntimeException e)
        {
            log.error("can't be null");
            return  new ResponseEntity<>("Restaurant can't be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateRestaurant(@RequestBody Restaurant restaurant) throws RestaurantNotFoundException {
        try {
            restaurantService.updateRestaurant(restaurant);
            return ResponseEntity.ok("updated successfully");
        }catch (RestaurantNotFoundException e)
        {
            return new ResponseEntity<>("restaurant doesn't exist", HttpStatus.CONFLICT);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/{city}")
    public ResponseEntity<?>getAllRestaurant(@PathVariable String city) throws CityNotFoundException
    {
        try {

            List<Restaurant> allRestaurant = restaurantService.getAllRestaurant(city);
            return new ResponseEntity<>(allRestaurant, HttpStatus.OK);
        }catch (CityNotFoundException e)
        {
            return new ResponseEntity<>("not found", HttpStatus.CONFLICT);
        }

    }


    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable String restaurantId) throws RestaurantNotFoundException {
        try {
            restaurantService.deleteRestaurant(restaurantId);

            return ResponseEntity.ok("Restaurant deleted successfully");
        }catch (RestaurantNotFoundException e)
        {
            e.printStackTrace();
            return new ResponseEntity<>("restaurant doesn't exist",HttpStatus.CONFLICT);
        }
        catch (RuntimeException e)
        {
            return  new ResponseEntity<>("something went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
