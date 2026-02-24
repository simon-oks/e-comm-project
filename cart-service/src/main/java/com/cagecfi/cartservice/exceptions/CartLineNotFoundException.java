package com.cagecfi.cartservice.exceptions;

public class CartLineNotFoundException extends RuntimeException {
    public CartLineNotFoundException(String message) {
        super(message);
    }
}
