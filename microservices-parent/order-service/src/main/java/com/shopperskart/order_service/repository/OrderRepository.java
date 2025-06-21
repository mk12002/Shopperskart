package com.shopperskart.order_service.repository;

import com.shopperskart.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Additional query methods can be defined here if needed

}
