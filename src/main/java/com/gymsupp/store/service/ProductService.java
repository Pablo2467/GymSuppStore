package com.gymsupp.store.service;

import com.gymsupp.store.model.Product;
import com.gymsupp.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    public List<Product> findAll() {
        return repository.findAll();
    }
    public Product save(Product product) {
        return repository.save(product);
    }
    public void delete(Long id) {
        repository.deleteById(id);
    }
    public Product findById(Long id) {
        return repository.findById(id).orElseThrow();
    }
}

