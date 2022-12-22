package com.stackroute.emailservice.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class MailRequest {

    private String restaurantId;
    private String restaurantName;
    private String userEmailId;
    private String ownerEmailId;
    private List<Integer> seatNumber;
    private Bookingstatus status;
    private String date;
    private String bookingId;

    private int numberOfPerson;

    private String address;
}







