package com.cagecfi.cartservice.mapper;

import com.cagecfi.cartservice.dtos.requests.CartLineRequest;
import com.cagecfi.cartservice.dtos.requests.CartLineUpdateRequest;
import com.cagecfi.cartservice.dtos.responses.CartLineResponse;
import com.cagecfi.cartservice.entity.Cart;
import com.cagecfi.cartservice.entity.CartLine;
import com.cagecfi.cartservice.feign.dtos.Product;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartLineMapper {
    public CartLine toEntity(CartLineRequest request, Cart cart, Product product){
        if (request == null) return null;
        return CartLine.create(
                cart,
                request.productId(),
                BigDecimal.valueOf(request.quantity()),
                product.price()
        );
    }

    public void update(CartLine line, CartLineUpdateRequest request){
        if (request == null) return;
        line.update(BigDecimal.valueOf(request.quantity()));
    }

    public CartLineResponse toResponse(CartLine line){
        if (line == null) return null;
        return new CartLineResponse(
                line.getId(),
                line.getProductId(),
                line.getUnitPrice(),
                line.getQuantity(),
                line.getLineTotal(),
                line.getCreatedAt() != null ? line.getCreatedAt().toString() : null,
                line.getUpdatedAt() != null ? line.getUpdatedAt().toString() : null
        );
    }
}
