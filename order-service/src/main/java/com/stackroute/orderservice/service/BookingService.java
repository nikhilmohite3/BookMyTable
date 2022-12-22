package com.stackroute.orderservice.service;

import com.stackroute.orderservice.models.Booking;
import com.stackroute.orderservice.models.Bookingstatus;
import com.stackroute.orderservice.repository.BookingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private SequenceGeneratorBookingService sequenceGeneratorService;

    public Booking saveBooking(Booking booking) {
        log.info("Inside saveBooking method of BookingService");
//        booking.setBookingId(sequenceGeneratorService.generateSequence(Booking.SEQUENCE_NAME));
//        booking.setBookingTime(LocalDateTime.now());
        return bookingRepository.save(booking);
    }
    public Booking cancelBooking(Long id){
        Query query = new Query();
        query.addCriteria(Criteria.where("bookingId").is(id));
        mongoTemplate.findOne(query, Booking.class);
        Update update = new Update();
        update.set("status",Bookingstatus.FAILED);
        mongoTemplate.updateFirst(query,update, Booking.class);
        Booking b = mongoTemplate.findOne(query, Booking.class);
        return b;
    }
}