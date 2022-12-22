package com.stackroute.registrationservice.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantAddress {

    private String buildingNo;
    private String streat;
    private String city;
    private String area;
    private String landMark;
    private String state;
    private int pincode;
}


