package com.cagecfi.orderservice.mapper;

import com.cagecfi.orderservice.dto.requests.OrderRequest;
import com.cagecfi.orderservice.dto.responses.OrderResponse;
import com.cagecfi.orderservice.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final OrderLineMapper orderLineMapper;

    public Order toEntity(OrderRequest request) {
        if (request == null) return null;
        Order order = Order.create(request.customerId());
        request.lines()
                .forEach(
                        line -> order.addOrderLine(orderLineMapper.toEntity(line, order))
                );
        return order;
    }

    public OrderResponse toResponse(Order order){
        if (order == null) return null;
        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getLines().stream()
                        .map(orderLineMapper::toResponse)
                        .toList(),
                order.getCreatedAt() != null ? order.getCreatedAt().toString() : null,
                order.getUpdatedAt() != null ? order.getUpdatedAt().toString() : null
        );
    }

}
