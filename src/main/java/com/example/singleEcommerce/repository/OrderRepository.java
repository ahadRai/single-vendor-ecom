package com.example.singleEcommerce.repository;

import com.example.singleEcommerce.model.Order;
import com.example.singleEcommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
