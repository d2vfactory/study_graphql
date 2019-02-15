package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import org.springframework.stereotype.Component;

public class Mutation implements GraphQLMutationResolver {

    private final ProductRepository productRepository;

    public Mutation(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product newProduct(String name) {
        Product product = new Product(name);
        productRepository.save(product);
        return product;
    }

    public boolean deleteProduct(long id) {
        productRepository.deleteById(id);
        return true;
    }

    public Product updateProductName(long id, String name) {
        Product product = productRepository.findById(id).orElse(null);
        product.setName(name);
        productRepository.save(product);
        return product;
    }
}
