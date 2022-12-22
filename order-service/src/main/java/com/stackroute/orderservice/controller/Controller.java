package com.stackroute.orderservice.controller;





import com.stackroute.orderservice.config.MessagingConfig;
import com.stackroute.orderservice.models.Booking;
import com.stackroute.orderservice.models.Order;
import com.stackroute.orderservice.service.BookingService;
import com.stackroute.orderservice.service.OrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class Controller {
    @Autowired
    OrderService orderService;
    @Autowired
    BookingService bookingService;

    @Autowired
    RabbitTemplate template;

    //Api to place order
    @PostMapping("/order")
    public Order  makeOrder(@RequestBody Order order) {
        orderService.saveOrder(order);
        return order;
    }
//Api to place booking
    @PostMapping ("/booking")
    public Booking  makeBooking(@RequestBody Booking booking) {
        template.convertAndSend(MessagingConfig.EXCHANGE, MessagingConfig.ROUTING_KEY, booking);
        bookingService.saveBooking(booking);
        return booking;
    }
    //Api to cancel Booking
    @GetMapping("/booking/{bookingId}")
    public Booking cancelBooking(@PathVariable Long bookingId){
        Booking cancelledBooking = bookingService.cancelBooking(bookingId);
        return cancelledBooking;
    }
    //Api to cancel Order
    @GetMapping("/order/{orderId}")
    public Order cancelorder(@PathVariable Long orderId){
        Order cancelledOrder = orderService.cancelOrder(orderId);
        return cancelledOrder;
    }

}
