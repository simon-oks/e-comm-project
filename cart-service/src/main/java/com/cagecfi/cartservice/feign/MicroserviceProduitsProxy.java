package com.cagecfi.cartservice.feign;

import com.cagecfi.cartservice.feign.dtos.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface MicroserviceProduitsProxy {
    @GetMapping("/products/{id}")
    Product getProductById(@PathVariable String id);
}
