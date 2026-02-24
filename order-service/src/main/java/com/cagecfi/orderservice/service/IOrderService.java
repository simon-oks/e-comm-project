package com.cagecfi.orderservice.service;

import com.cagecfi.orderservice.dto.requests.OrderRequest;
import com.cagecfi.orderservice.dto.responses.OrderResponse;
import com.cagecfi.orderservice.entity.Order;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse getOrderById(String id);
    List<OrderResponse> getAllCustomerOrders(String customerId);

    Order findByCustomerId(String customerId);
    Order findById(String id);
}
