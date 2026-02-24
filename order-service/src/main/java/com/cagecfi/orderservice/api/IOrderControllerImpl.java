package com.cagecfi.orderservice.api;

import com.cagecfi.orderservice.dto.requests.OrderRequest;
import com.cagecfi.orderservice.dto.responses.OrderResponse;
import com.cagecfi.orderservice.service.IOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Tag(name = "Gestion des commandes des client")
public class IOrderControllerImpl implements IOrderController {

    private final IOrderService service;

    @Override
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(orderRequest));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable String id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @Override
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getAllCustomerOrders(
            @PathVariable String customerId) {
        return ResponseEntity.ok(service.getAllCustomerOrders(customerId));
    }
}
