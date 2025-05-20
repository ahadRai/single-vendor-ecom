package com.example.singleEcommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class OrderRequest {
    private List<OrderItemRequest> items;
}
