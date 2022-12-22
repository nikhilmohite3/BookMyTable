package com.stackroute.registrationservice.ServiceTest;

import com.stackroute.registrationservice.entity.RestaurantOwner;
import com.stackroute.registrationservice.entity.User;
import com.stackroute.registrationservice.execption.*;
import com.stackroute.registrationservice.repository.RestaurantOwnerRepo;
import com.stackroute.registrationservice.serviceImpl.RestaurantOwnerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantOwnerServiceTest {
    @Mock
    RestaurantOwnerRepo restaurantOwnerRepo;
    @InjectMocks
    RestaurantOwnerServiceImpl restaurantOwnerService;

    @Test
    public void restaurantOwnerRegister() throws OwnerAllReadyExist {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "shubham", "singh",8002970262L, "siwan");
        when(restaurantOwnerService.registerOwner(restaurantOwner)).thenReturn(restaurantOwner);
        assertEquals(restaurantOwner, restaurantOwnerService.registerOwner(restaurantOwner));
    }

    @Test
    public void restaurantOwnerRegisterFail()
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner("owner@gmail.com", "shubham", "singh",8002970262L, "siwan");
        String email ="owner@gmail.com";
        when(restaurantOwnerRepo.existsById(email)).thenReturn(true);
        assertThrows(OwnerAllReadyExist.class,()->restaurantOwnerService.registerOwner(restaurantOwner));


    }

    @Test
    public void updateRestaurantOwner() throws RestaurantNotFoundException, OwnerNotFoundException {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setEmailId("owner@gmail.com");
        restaurantOwner.setFirstName("owner");
        restaurantOwner.setLastName("singh");
        restaurantOwner.setMobNo(9304848904L);
        restaurantOwner.setAddress("siwan");
        when(restaurantOwnerRepo.findById(restaurantOwner.getEmailId())).thenReturn(Optional.of(restaurantOwner));
        when(restaurantOwnerRepo.save(restaurantOwner)).thenReturn(restaurantOwner);
        assertEquals(restaurantOwner, restaurantOwnerService.updateOwner(restaurantOwner, restaurantOwner.getEmailId()));
    }

    @Test
    public void updateRestaurantOwnerFail()
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setEmailId("owner@gmail.com");
        restaurantOwner.setFirstName("owner");
        restaurantOwner.setLastName("singh");
        restaurantOwner.setMobNo(798888797);
        restaurantOwner.setAddress("siwan");
        String email = "owner@gmail.com";
        when(restaurantOwnerRepo.findById(email))
                .thenReturn(Optional.empty());
        assertThrows(OwnerNotFoundException.class,()->restaurantOwnerService.updateOwner(restaurantOwner, email));
    }


    @Test
    public void deleteRestaurantOwner() throws OwnerNotFoundException {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setEmailId("owner@gmail.com");
        restaurantOwner.setFirstName("owner");
        restaurantOwner.setLastName("singh");
        restaurantOwner.setMobNo(9304848904L);
        restaurantOwner.setAddress("siwan");

        when(restaurantOwnerRepo.existsById(restaurantOwner.getEmailId())).thenReturn(true);
        restaurantOwnerRepo.deleteById(restaurantOwner.getEmailId());
        verify(restaurantOwnerRepo, times(1)).deleteById(restaurantOwner.getEmailId());
        assertEquals("deleted successfully", restaurantOwnerService.deleteOwner(restaurantOwner.getEmailId()));
    }
    @Test
    public void deleteRestaurantOwnerFail()
    {
        RestaurantOwner restaurantOwner = new RestaurantOwner();
        restaurantOwner.setEmailId("owner@gmail.com");
        restaurantOwner.setFirstName("owner");
        restaurantOwner.setLastName("singh");
        restaurantOwner.setMobNo(9304848904L);
        restaurantOwner.setAddress("siwan");

        String email ="singh@gmail.com";
        restaurantOwnerRepo.deleteById(restaurantOwner.getEmailId());
        assertThrows(OwnerNotFoundException.class,()->restaurantOwnerService.deleteOwner(restaurantOwner.getEmailId()));

    }


}
