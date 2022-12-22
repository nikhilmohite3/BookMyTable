package com.stackroute.orderservice.service;

import com.stackroute.orderservice.models.Order;
import com.stackroute.orderservice.models.OrderStatus;
import com.stackroute.orderservice.repository.OrderRepository;
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
    public class OrderService {

        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        MongoTemplate mongoTemplate;

        @Autowired
        private SequenceGeneratorOrderService sequenceGeneratorService;

        public Order saveOrder(Order order) {

    order.setOrderId(sequenceGeneratorService.generateSequence(Order.SEQUENCE_NAME));
    order.setOrderTime(LocalDateTime.now());
    return orderRepository.save(order);

        }
        public Order cancelOrder(Long orderId){
            Query query = new Query();
            query.addCriteria(Criteria.where("orderId").is(orderId));
            mongoTemplate.findOne(query, Order.class);
            Update update = new Update();
            update.set("status", OrderStatus.CANCELLED);
            mongoTemplate.updateFirst(query,update, Order.class);
            Order order = mongoTemplate.findOne(query, Order.class);
            return order;

        }
    }