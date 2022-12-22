package com.stackroute.orderservice.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Transient
    public static final String SEQUENCE_NAME = "users_sequence_order";
    private Long orderId;
    private String restaurantId;
    private String userEmailId;
    private String ownerEmailId;
    private String userName;
    private int seatNumber;
    private List<String> menu;
    private OrderStatus status;
    private Date date;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime orderTime;


}
