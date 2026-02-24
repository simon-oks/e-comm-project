package com.cagecfi.orderservice.api;

import com.cagecfi.orderservice.dto.requests.OrderRequest;
import com.cagecfi.orderservice.dto.responses.OrderResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderController {
    ResponseEntity<OrderResponse> createOrder(@Valid OrderRequest orderRequest);
    ResponseEntity<OrderResponse> getOrderById(String id);
    ResponseEntity<List<OrderResponse>> getAllCustomerOrders(String customerId);
}
