package com.cagecfi.cartservice.feign.exceptions;

import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        if (response.status() == 400){
            if (response.body().toString().contains("PRODUCT_NOT_FOUND")){
                return new ProductNotFoundException("Requête incorrect: Produit non existant.");
            }else if (response.body().toString().contains("CUSTOMER_NOT_FOUND")){
                return new CustomerNotFoundException("Requête non trouvé");
            }else {
                return new ResourceNotFoundException("La resource demandé est introuvable");
            }
        }
        return defaultErrorDecoder.decode(s, response);
    }
}
