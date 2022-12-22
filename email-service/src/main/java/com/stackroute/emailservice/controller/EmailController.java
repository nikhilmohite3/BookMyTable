package com.stackroute.emailservice.controller;

import com.stackroute.emailservice.dto.MailRequest;
import com.stackroute.emailservice.dto.MailResponse;
import com.stackroute.emailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import com.stackroute.emailservice.config.MessagingConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EmailController {
    @Autowired
    private EmailService service;

   // @PostMapping("/sendingEmail")
//    ResponseEntity<MailResponse> sendEmail(@RequestBody MailRequest request) {
//
//        Map<String, Object> model = new HashMap<>();
//        model.put("restaurantId", request.getRestaurantId());
//        model.put("restaurantName", request.getRestaurantName());
//        model.put("SeatNumbers", request.getSeatNumber());
//        model.put("status", request.getStatus());
//        model.put("date", request.getDate());
//        model.put("bookingId", request.getBookingId());
//        model.put("numberOfPerson", request.getNumberOfPerson());
//        model.put("address", request.getAddress());
//
//
//        MailResponse response = service.sendEmail(request, model);
//        return new ResponseEntity<MailResponse>(response, HttpStatus.OK);
//    }


    //    @PostMapping("/sendingEmail")
    @RabbitListener(queues = MessagingConfig.QUEUE)
    void sendEmail(@RequestBody MailRequest request) {
        Map<String, Object> model = new HashMap<>();
        model.put("restaurantId", request.getRestaurantId());
        model.put("restaurantName", request.getRestaurantName());
        model.put("SeatNumbers", request.getSeatNumber());
        model.put("status", request.getStatus());
        model.put("date", request.getDate());
        model.put("bookingId", request.getBookingId());
        model.put("numberOfPerson", request.getNumberOfPerson());
        model.put("address", request.getAddress());

        MailResponse response= service.sendEmail(request, model);
    }
}
