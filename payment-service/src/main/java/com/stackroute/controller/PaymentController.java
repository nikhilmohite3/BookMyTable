package com.stackroute.controller;

import java.util.List;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stackroute.dto.Jsonobject;
import com.stackroute.model.Payment;
import com.stackroute.repository.PaymentRepository;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping ("/payment")

public class PaymentController {

    @Autowired
    private Environment env;
    @Autowired
    private PaymentRepository paymentRepository;

    @PostMapping("/createOrderId")
    public String createPaymentOrder(@RequestBody Jsonobject jsonobject, ModelMap model) {
        String order_id = null;
        ModelAndView mav = null;

        try {
            RazorpayClient razorpay  = new  RazorpayClient("rzp_test_a7KULMUGyiT9rx", "7KJ9CuBrcPlsTkZtVh5mLj8D");
            JSONObject orderRequest = new JSONObject();

            orderRequest.put("amount", jsonobject.getAmnt()*100); // amount in the smallest currency
            orderRequest.put("currency",jsonobject.getCrncy());
            orderRequest.put("receipt", jsonobject.getRcid());


            Order order = razorpay.orders.create(orderRequest);

            order_id = order.get("id");

            model.addAttribute("order_id", order_id);

            Payment p = new Payment();
            p.setOrder_id(order_id);
            p.setAmount(Double.valueOf(jsonobject.getAmnt()));
            p.setCurrency(order.get("currency"));
            p.setReceiptNumber(order.get("receipt"));
            p.setStatus(order.get("status"));
            p.setBooking_id(jsonobject.getBkid());
            p.setUser_mail_id(jsonobject.getEmailid());
            p.setTransaction_id(jsonobject.getTrsid());//
            paymentRepository.save(p);

        } catch (RazorpayException e) {

            System.out.println(e.getMessage());
        }
        return order_id;
    }
}




