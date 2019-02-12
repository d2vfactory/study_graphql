package com.example.graphql.specification;

import com.example.graphql.entity.Product;

import java.util.List;

public interface ProductDetails {

    List<Product> findAll();

    Product findById(long id);
}
