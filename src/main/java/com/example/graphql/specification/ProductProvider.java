package com.example.graphql.specification;

import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductProvider implements ProductDetails {

    private final ProductRepository repository;

    public ProductProvider(ProductRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findById(long id) {
        return repository.findById(id).get();
    }
}
