package com.cagecfi.cartservice.api;

import com.cagecfi.cartservice.dtos.requests.CartLineRequest;
import com.cagecfi.cartservice.dtos.requests.CartLineUpdateRequest;
import com.cagecfi.cartservice.dtos.requests.CartRequest;
import com.cagecfi.cartservice.dtos.responses.CartLineResponse;
import com.cagecfi.cartservice.dtos.responses.CartResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface ICartController {
    ResponseEntity<CartResponse> getCartByCustomerId(String customerId);
    ResponseEntity<CartResponse> createCart(@Valid CartRequest cartRequest);
    ResponseEntity<Void> clearCustomerCart(String customerId);

    ResponseEntity<CartLineResponse> addCartLine(String cartId, @Valid CartLineRequest request);
    ResponseEntity<CartLineResponse> updateCartLine(String cartId, String cartLineId, @Valid CartLineUpdateRequest request);
    ResponseEntity<Void> removeCartLine(String cartId, String cartLineId);
}
