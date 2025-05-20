package com.example.singleEcommerce.service;

import com.example.singleEcommerce.dto.OrderItemRequest;
import com.example.singleEcommerce.dto.OrderRequest;
import com.example.singleEcommerce.model.Order;
import com.example.singleEcommerce.model.OrderItem;
import com.example.singleEcommerce.model.Product;
import com.example.singleEcommerce.model.User;
import com.example.singleEcommerce.repository.OrderRepository;
import com.example.singleEcommerce.repository.ProductRepository;
import com.example.singleEcommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public Order placeOrder(String username, OrderRequest orderRequest) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderItemRequest itemRequest : orderRequest.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Insufficient stock for " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setPrice(product.getPrice() * itemRequest.getQuantity());
            item.setOrder(order);
            orderItems.add(item);

            total += item.getPrice();

            // reduce stock
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
        }

        order.setItems(orderItems);
        order.setTotalPrice(total);

        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return orderRepository.findByUser(user);
    }
}

