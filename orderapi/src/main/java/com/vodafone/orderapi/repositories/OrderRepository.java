package com.vodafone.orderapi.repositories;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
