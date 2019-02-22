package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductQuery implements GraphQLQueryResolver {

    private final ProductRepository productRepository;

    public ProductQuery(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    // query
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
