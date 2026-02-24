package com.cagecfi.orderservice.exceptions;

public class OderLineNotFoundException extends RuntimeException {
    public OderLineNotFoundException(String message) {
        super(message);
    }
}
