package com.cagecfi.productservice.service;

import com.cagecfi.productservice.dto.requests.ProductRequest;
import com.cagecfi.productservice.dto.responses.ProductResponse;
import com.cagecfi.productservice.entity.Product;

import java.util.List;

public interface IProductService {
    ProductResponse create(ProductRequest request);
    ProductResponse update(String id, ProductRequest request);
    void delete(String id);
    ProductResponse get(String id);
    List<ProductResponse> getAll();
    List<ProductResponse> search(String searchKey);
    List<ProductResponse> getByCategory(String categoryId);

    Product findByIdAndDeletedIsFalse(String id);
}
