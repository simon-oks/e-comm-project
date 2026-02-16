package com.cagecfi.productservice.service.impl;

import com.cagecfi.productservice.dto.requests.CategoryRequest;
import com.cagecfi.productservice.dto.responses.CategoryResponse;
import com.cagecfi.productservice.entity.Category;
import com.cagecfi.productservice.exception.CategoryNotFoundException;
import com.cagecfi.productservice.mapper.CategoryMapper;
import com.cagecfi.productservice.repository.CategoryRepository;
import com.cagecfi.productservice.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ICategoryServiceImpl implements ICategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        log.info("Creating category: libelle = {}", request.libelle());
        Optional<Category> byLibelle = repository.findByLibelle(request.libelle());
        if (byLibelle.isEmpty()) {
            Category category = repository.saveAndFlush(mapper.toEntity(request));
            return mapper.toResponse(category);
        }else {
            Category c = byLibelle.get();
            if (c.getDeleted()) {
                c.unDelete();
                return mapper.toResponse(repository.save(c));
            }
            return mapper.toResponse(c);
        }
    }

    @Override
    @Transactional
    public CategoryResponse update(String id, CategoryRequest request) {
        log.info("Updating category: id = {}", id);
        Category category = findByIdAndDeletedIsFalse(id);
        mapper.update(category, request);
        return mapper.toResponse(repository.save(category));
    }

    @Override
    @Transactional
    public void delete(String id) {
        log.info("Deleting category: id = {}", id);
        Category category = findByIdAndDeletedIsFalse(id);
        category.delete();
        repository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse get(String id) {
        log.info("Getting category: id = {}", id);
        Category category = findByIdAndDeletedIsFalse(id);
        return mapper.toResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAll() {
        log.info("Getting all no deleted categories");
        return repository.findAllByDeletedIsFalse().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<CategoryResponse> search(String searchKey) {
        log.info("Searching categories: searchKey = {}", searchKey);
        return repository.searchByLibelleContainingIgnoreCaseAndDeletedIsFalse(searchKey).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getByLibelle(String libelle) {
        return repository.findByLibelle(libelle);
    }

    @Override
    @Transactional(readOnly = true)
    public Category findByIdAndDeletedIsFalse(String id) {
        return repository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(() -> new CategoryNotFoundException("Catégorie non trouvée"));
    }
}
