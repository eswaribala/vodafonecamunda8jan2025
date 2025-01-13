package com.vodafone.orderapi.repositories;

import com.vodafone.orderapi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
