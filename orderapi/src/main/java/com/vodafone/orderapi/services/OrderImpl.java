package com.vodafone.orderapi.services;

import com.vodafone.orderapi.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
public class OrderImpl  implements  OrderDao{
    @Autowired
    private OrderRepository orderRepository;
    @Override
    public Order addOrder(Order order) {
        return this.orderRepository.save(order);
    }
}
