package com.cagecfi.cartservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;

@SpringBootApplication
@EnableFeignClients
public class CartServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
        BigDecimal amount = BigDecimal.TEN;
        if (amount.compareTo(BigDecimal.ZERO) < 0){
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

}
