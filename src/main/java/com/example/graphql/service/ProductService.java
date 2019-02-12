package com.example.graphql.service;

import com.example.graphql.specification.ProductDetails;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDetails productDetails;

    public ProductService(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }


}
