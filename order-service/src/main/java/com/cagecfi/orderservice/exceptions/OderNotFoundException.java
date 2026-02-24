package com.cagecfi.orderservice.exceptions;

public class OderNotFoundException extends RuntimeException{
    public OderNotFoundException(String message){
        super(message);
    }
}
