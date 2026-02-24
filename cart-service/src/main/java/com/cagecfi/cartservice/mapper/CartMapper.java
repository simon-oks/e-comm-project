package com.cagecfi.cartservice.mapper;

import com.cagecfi.cartservice.dtos.responses.CartResponse;
import com.cagecfi.cartservice.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final CartLineMapper cartLineMapper;

    public Cart toEntity(String customerId){
        if (customerId == null) return null;
        return Cart.create(customerId);
    }

    public CartResponse toResponse(Cart cart){
        if (cart == null) return null;
        return new CartResponse(
                cart.getId(),
                cart.getCustomerId(),
                cart.getLines().stream()
                        .map(cartLineMapper::toResponse)
                        .toList(),
                cart.getCreatedAt() != null ? cart.getCreatedAt().toString() : null,
                cart.getUpdatedAt() != null ? cart.getUpdatedAt().toString() : null
        );
    }
}
