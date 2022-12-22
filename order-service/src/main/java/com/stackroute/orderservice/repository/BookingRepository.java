package com.stackroute.orderservice.repository;

import com.stackroute.orderservice.models.Booking;
import com.stackroute.orderservice.models.Bookingstatus;
import com.stackroute.orderservice.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, Long> {
    @Query(value="{'bookingId' : $0}", delete = true)
    public Booking deleteAllByBookingId(Long bookingId);
}
