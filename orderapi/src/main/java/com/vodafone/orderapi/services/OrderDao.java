package com.vodafone.orderapi.services;

import com.vodafone.orderapi.exceptions.OrderNotFoundException;
import com.vodafone.orderapi.models.Order;

public interface OrderDao {

    Order addOrder(Order order);
    Order updateOrder(long orderId, long amount) throws OrderNotFoundException;
}
