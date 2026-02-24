package com.cagecfi.cartservice.service;

import com.cagecfi.cartservice.dtos.requests.CartLineRequest;
import com.cagecfi.cartservice.dtos.requests.CartLineUpdateRequest;
import com.cagecfi.cartservice.dtos.requests.CartRequest;
import com.cagecfi.cartservice.dtos.responses.CartLineResponse;
import com.cagecfi.cartservice.dtos.responses.CartResponse;

public interface ICartService {
    CartResponse getCartByCustomerId(String customerId);
    CartResponse createCart(CartRequest cartRequest);
    void clearCustomerCart(String customerId);

    CartLineResponse addCartLine(String cartId, CartLineRequest request);
    CartLineResponse updateCartLine(String cartId, String cartLineId, CartLineUpdateRequest request);
    void removeCartLine(String cartId, String cartLineId);
}
