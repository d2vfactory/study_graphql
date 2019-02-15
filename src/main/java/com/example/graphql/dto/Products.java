package com.example.graphql.dto;

import com.example.graphql.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class Products {

    List<Product> products;

}
