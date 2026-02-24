package com.cagecfi.orderservice.service.impl;

import com.cagecfi.orderservice.dto.requests.OrderRequest;
import com.cagecfi.orderservice.dto.responses.OrderResponse;
import com.cagecfi.orderservice.entity.Order;
import com.cagecfi.orderservice.exceptions.OderNotFoundException;
import com.cagecfi.orderservice.mapper.OrderMapper;
import com.cagecfi.orderservice.repository.OrderRepository;
import com.cagecfi.orderservice.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IOrderServiceImpl implements IOrderService {

    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("Creating order for customer ID = {}", orderRequest.customerId());
        Order order = orderMapper.toEntity(orderRequest);
        orderRepository.save(order);
        return orderMapper.toResponse(order);
    }

    @Override
    public OrderResponse getOrderById(String id) {
        log.info("Getting Order by ID = {}", id);
        return orderMapper.toResponse(findById(id));
    }

    @Override
    public List<OrderResponse> getAllCustomerOrders(String customerId) {
        return orderRepository.findAllByCustomerId(customerId)
                .stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Override
    public Order findByCustomerId(String customerId) {
        log.info("Fetching orders for customer ID = {}", customerId);
        return orderRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new OderNotFoundException("Customer not found"));
    }

    @Override
    public Order findById(String id) {
        log.info("Fetching order for ID = {}", id);
        return orderRepository.findById(id)
                .orElseThrow(() -> new OderNotFoundException("Order not found"));
    }
}
