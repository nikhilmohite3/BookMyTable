package com.stackroute.registrationservice.controller;

import com.stackroute.registrationservice.entity.RestaurantOwner;
import com.stackroute.registrationservice.execption.OwnerAllReadyExist;
import com.stackroute.registrationservice.execption.OwnerNotFoundException;
import com.stackroute.registrationservice.service.RestaurantOwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class RestaurantOwnerController {

    @Autowired
    private RestaurantOwnerService restaurantOwnerService;

    @PostMapping
    public ResponseEntity<String> registerOwner(@RequestBody RestaurantOwner restaurantOwner) throws  OwnerAllReadyExist
    {
        try {
            log.info("Owner Created");
            restaurantOwnerService.registerOwner(restaurantOwner);
            return  new ResponseEntity<>("RestaurantOwner Register", HttpStatus.CREATED);
        }catch (OwnerAllReadyExist e)
        {
            log.error("Owner All ready exist");
            e.printStackTrace();
            return  new ResponseEntity<>("Owner all ready Exist", HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            log.error("can't be null");
            return  new ResponseEntity<>("Owner can't be null", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{emailId}")
    public ResponseEntity<String> updateOwner(@RequestBody RestaurantOwner restaurantOwner, @PathVariable String emailId) throws OwnerNotFoundException{

        try {
            log.info("Updating Owner");

            restaurantOwnerService.updateOwner(restaurantOwner, emailId);
            return ResponseEntity.ok("Updated successfully");
        }catch (OwnerNotFoundException e)
        {
            log.error("Owner doesn't exist");
            e.printStackTrace();
            return new ResponseEntity<>("RestaurantOwner doesn't exist", HttpStatus.CONFLICT);
        }
        catch (RuntimeException e)
        {
            return new ResponseEntity<>("Something  went wording", HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @DeleteMapping("/{emailId}")
    public ResponseEntity<String> deleteRestaurantOwner(@PathVariable String emailId) throws OwnerNotFoundException {

        try {
            log.info("Deleting RestaurantOwner");
            restaurantOwnerService.deleteOwner(emailId);
            return ResponseEntity.ok("Owner Delete successfully");
        }catch (OwnerNotFoundException e)
        {
            log.error("RestaurantOwner doesn't exits");
            e.printStackTrace();
           return new ResponseEntity<>("owner doesn't exist", HttpStatus.CONFLICT);
         //   return ResponseEntity.ok("owner doesn't exist");
        }
        catch (Exception e)
        {
            log.error("Something went wrong");
            return  new ResponseEntity<>("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
