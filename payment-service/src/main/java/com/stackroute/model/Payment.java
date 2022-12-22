package com.stackroute.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @Entity
    @Table(name="Payment")
    public class Payment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long paymentid;
        private String order_id;
        private String transaction_id;
        private String booking_id;
        private String user_mail_id;
        private double amount;
        private String currency;
        private String receiptNumber;
        private String status;

        @Enumerated(EnumType.STRING)
        private Payment_option payment_option;

    }
