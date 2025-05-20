package com.example.singleEcommerce.controller;

import com.example.singleEcommerce.model.Order;
import com.example.singleEcommerce.repository.OrderRepository;
import lombok.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders/admin")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderRepository orderRepository;

    @GetMapping("/all")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}

