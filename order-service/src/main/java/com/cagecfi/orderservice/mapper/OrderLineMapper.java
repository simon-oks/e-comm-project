package com.cagecfi.orderservice.mapper;

import com.cagecfi.orderservice.dto.requests.OrderLineRequest;
import com.cagecfi.orderservice.dto.responses.OrderLineResponse;
import com.cagecfi.orderservice.entity.Order;
import com.cagecfi.orderservice.entity.OrderLine;
import org.springframework.stereotype.Component;

@Component
public class OrderLineMapper {
    public OrderLine toEntity(OrderLineRequest request, Order order){
        if (request == null) return null;
        return OrderLine.create(order, request.productId(), request.quantity(), request.unitPrice());
    }

    public OrderLineResponse toResponse(OrderLine line){
        if (line == null) return null;
        return new OrderLineResponse(
                line.getId(),
                line.getProductId(),
                line.getQuantity(),
                line.getUnitPrice(),
                line.getLineTotalPrice()
        );
    }
}
