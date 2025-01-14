package com.vodafone.orderapi.services;

import org.springframework.core.annotation.Order;

public interface OrderDao {
    Order addOrder(Order order);
}
