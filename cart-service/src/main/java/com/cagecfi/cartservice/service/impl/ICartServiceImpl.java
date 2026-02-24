package com.cagecfi.cartservice.service.impl;

import com.cagecfi.cartservice.dtos.requests.CartLineRequest;
import com.cagecfi.cartservice.dtos.requests.CartLineUpdateRequest;
import com.cagecfi.cartservice.dtos.requests.CartRequest;
import com.cagecfi.cartservice.dtos.responses.CartLineResponse;
import com.cagecfi.cartservice.dtos.responses.CartResponse;
import com.cagecfi.cartservice.entity.Cart;
import com.cagecfi.cartservice.entity.CartLine;
import com.cagecfi.cartservice.exceptions.CartLineNotFoundException;
import com.cagecfi.cartservice.exceptions.CartNotFoundException;
import com.cagecfi.cartservice.feign.MicroserviceCustomerProxy;
import com.cagecfi.cartservice.feign.MicroserviceProduitsProxy;
import com.cagecfi.cartservice.feign.dtos.Customer;
import com.cagecfi.cartservice.feign.dtos.Product;
import com.cagecfi.cartservice.mapper.CartLineMapper;
import com.cagecfi.cartservice.mapper.CartMapper;
import com.cagecfi.cartservice.repository.CartRepository;
import com.cagecfi.cartservice.service.ICartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ICartServiceImpl implements ICartService {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final CartLineMapper cartLineMapper;
    private final MicroserviceCustomerProxy msCustomerProxy;
    private final MicroserviceProduitsProxy msProduitsProxy;


    @Override
    @Transactional(readOnly = true)
    public CartResponse getCartByCustomerId(String customerId) {
        log.info("Getting customer cart by id: ID = {}", customerId);
        return cartMapper.toResponse(findCardByCustomerId(customerId));
    }

    @Override
    @Transactional
    public CartResponse createCart(CartRequest cartRequest) {
        log.info("Creating cart for customer: ID = {}", cartRequest.customerId());
        // Récupérer le client
        Customer customer = msCustomerProxy.getCustomerById(cartRequest.customerId());
        Cart cart = cartMapper.toEntity(customer.id());

        for (CartLineRequest lineRequest: cartRequest.lines()){
            // Récupérer le produit
            Product product = msProduitsProxy.getProductById(lineRequest.productId());
            CartLine cartLine = cartLineMapper.toEntity(lineRequest, cart, product);
            cart.getLines().add(cartLine);
        }
        cart = cartRepository.save(cart);
        return cartMapper.toResponse(cart);
    }

    @Override
    @Transactional
    public void clearCustomerCart(String customerId) {
        log.info("Cleaning a customer cart");
        Cart cart = findCardByCustomerId(customerId);
        cart.getLines().clear();
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public CartLineResponse addCartLine(String cartId, CartLineRequest request) {
        log.info("Adding line to a cart");
        Cart cart = findCardById(cartId);
        // Récupérer le produit
        Product product = msProduitsProxy.getProductById(request.productId());
        CartLine cartLine = cartLineMapper.toEntity(request, cart, product);
        cart.getLines().add(cartLine);
        cart = cartRepository.save(cart);
        return cartLineMapper.toResponse(getCartLineWithProductId(cart.getLines(), request.productId()));
    }

    @Override
    @Transactional
    public CartLineResponse updateCartLine(String cartId, String cartLineId, CartLineUpdateRequest request) {
        log.info("Updating cart line with id = {}", cartLineId);
        Cart cart = findCardById(cartId);
        CartLine cartLine = getCartLineWithId(cart.getLines(), cartLineId);
        cartLineMapper.update(cartLine, request);
        cart = cartRepository.save(cart);
        return cartLineMapper.toResponse(getCartLineWithId(cart.getLines(), cartLineId));
    }

    @Override
    @Transactional
    public void removeCartLine(String cartId, String cartLineId) {
        log.info("Removing cart line with id = {}", cartLineId);
        Cart cart = findCardById(cartId);
        CartLine cartLine = getCartLineWithId(cart.getLines(), cartLineId);
        cart.getLines().remove(cartLine);
        cartRepository.save(cart);
    }


    @Transactional(readOnly = true)
    public Cart findCardByCustomerId(String customerId){
        return cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }

    @Transactional(readOnly = true)
    public Cart findCardById(String cartId){
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found"));
    }


    CartLine getCartLineWithProductId(List<CartLine> cartLines, String productId){
        Optional<CartLine> optionalCartLine = cartLines.stream().filter(
                c -> Objects.equals(c.getProductId(), productId)
        ).findFirst();
        if (optionalCartLine.isEmpty())
            throw new CartLineNotFoundException("Cart line not found");
        return optionalCartLine.get();
    }

    CartLine getCartLineWithId(List<CartLine> cartLines, String cartLineId){
        Optional<CartLine> optionalCartLine = cartLines.stream().filter(
                c -> Objects.equals(c.getId(), cartLineId)
        ).findFirst();
        if (optionalCartLine.isEmpty())
            throw new CartLineNotFoundException("Cart line not found");
        return optionalCartLine.get();
    }

}
