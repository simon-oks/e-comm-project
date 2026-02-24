package com.cagecfi.cartservice.api;

import com.cagecfi.cartservice.dtos.requests.CartLineRequest;
import com.cagecfi.cartservice.dtos.requests.CartLineUpdateRequest;
import com.cagecfi.cartservice.dtos.requests.CartRequest;
import com.cagecfi.cartservice.dtos.responses.CartLineResponse;
import com.cagecfi.cartservice.dtos.responses.CartResponse;
import com.cagecfi.cartservice.service.ICartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
@Tag(name = "Gestion des paniers des client")
public class ICartControllerImpl implements ICartController {

    private final ICartService service;

    @Override
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<CartResponse> getCartByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(service.getCartByCustomerId(customerId));
    }

    @Override
    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody @Valid CartRequest cartRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCart(cartRequest));
    }

    @Override
    @DeleteMapping("/customer/{customerId}")
    @Operation(summary = "Vide la liste des lignes du panier")
    public ResponseEntity<Void> clearCustomerCart(@PathVariable String customerId) {
        service.clearCustomerCart(customerId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PostMapping("/{cartId}/lines")
    public ResponseEntity<CartLineResponse> addCartLine(
            @PathVariable String cartId,
            @RequestBody @Valid CartLineRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addCartLine(cartId, request));
    }

    @Override
    @PutMapping("/{cartId}/lines/{cartLineId}")
    public ResponseEntity<CartLineResponse> updateCartLine(
            @PathVariable String cartId,
            @PathVariable String cartLineId,
            @RequestBody @Valid CartLineUpdateRequest request) {
        return ResponseEntity.ok(service.updateCartLine(cartId, cartLineId, request));
    }

    @Override
    @DeleteMapping("/{cartId}/lines/{cartLineId}")
    public ResponseEntity<Void> removeCartLine(
            @PathVariable String cartId,
            @PathVariable String cartLineId
    ) {
        service.removeCartLine(cartId, cartLineId);
        return ResponseEntity.noContent().build();
    }
}
