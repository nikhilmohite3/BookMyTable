package com.stackroute.orderservice.repository;


import com.stackroute.orderservice.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface OrderRepository extends MongoRepository<Order, Long> {

//        Booking findByBookingId(Long bookingId);
//
//        List<Booking> findAllByUserId(Long userId);
//
//        List<Booking> findAllByPropertyId(Long propertyId);
    }
