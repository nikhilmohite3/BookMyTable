package com.stackroute.orderservice.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    private String restaurantId;
    private String restaurantName;
    private String userEmailId;
    private String ownerEmailId;
    private List<Integer> seatNumber;
    private Bookingstatus status;
    private String date;
    private String bookingId;
    private int noOfPerson;
    private String address;

    }
