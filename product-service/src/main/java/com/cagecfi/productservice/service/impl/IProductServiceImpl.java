package com.cagecfi.productservice.service.impl;

import com.cagecfi.productservice.dto.requests.ProductRequest;
import com.cagecfi.productservice.dto.responses.ProductResponse;
import com.cagecfi.productservice.entity.Category;
import com.cagecfi.productservice.entity.Product;
import com.cagecfi.productservice.exception.ProductNotFoundException;
import com.cagecfi.productservice.mapper.ProductMapper;
import com.cagecfi.productservice.repository.ProductRepository;
import com.cagecfi.productservice.service.ICategoryService;
import com.cagecfi.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class IProductServiceImpl implements IProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final ICategoryService categoryService;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        log.info("Creating product: name = {}", request.name());
        Category category = categoryService.findByIdAndDeletedIsFalse(request.categoryId());
        Product product = mapper.toEntity(request, category);
        product = repository.saveAndFlush(product);
        return mapper.toResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse update(String id, ProductRequest request) {
        log.info("Updating product: id = {}", id);
        Product product = findByIdAndDeletedIsFalse(id);
        Category category = categoryService.findByIdAndDeletedIsFalse(request.categoryId());
        mapper.update(request, product, category);
        return mapper.toResponse(repository.save(product));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting product: id = {}", id);
        Product product = findByIdAndDeletedIsFalse(id);
        product.delete();
        repository.save(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse get(String id) {
        log.info("Getting product: id = {}", id);
        Product product = findByIdAndDeletedIsFalse(id);
        return mapper.toResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAll() {
        log.info("Getting all no deleted products");
        return repository.findAllByDeletedIsFalse().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> search(String searchKey) {
        log.info("Searching products: searchKey = {}", searchKey);
        return repository
                .searchByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCaseAndDeletedIsFalse(searchKey, searchKey)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getByCategory(String categoryId) {
        log.info("Getting products by category: categoryId = {}", categoryId);
        return repository.findAllByCategory_IdAndDeletedIsFalse(categoryId).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Product findByIdAndDeletedIsFalse(String id) {
        return repository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new ProductNotFoundException("Produit non trouvé"));
    }
}
