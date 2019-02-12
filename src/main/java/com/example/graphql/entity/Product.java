package com.example.graphql.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    public Product(String name) {
        this.name = name;
    }

}
