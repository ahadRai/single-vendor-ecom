package com.example.singleEcommerce.controller;
import com.example.singleEcommerce.dto.OrderRequest;
import com.example.singleEcommerce.model.Order;
import com.example.singleEcommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Order> placeOrder(@RequestBody OrderRequest request,
                                            Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderService.placeOrder(username, request));
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> getUserOrders(Authentication authentication) {
        String username = authentication.getName();
        return ResponseEntity.ok(orderService.getUserOrders(username));
    }
}
