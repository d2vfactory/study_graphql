package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;

import java.util.List;

public class Query implements GraphQLQueryResolver {

    private final ProductRepository productRepository;

    public Query(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> products() {
        return productRepository.findAll();
    }

    public Product product(long id) {
        return productRepository.findById(id).orElse(null);
    }

    public long countProducts() {
        return productRepository.count();
    }


}
