package com.example.graphql.repository;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;

    @Before
    public void setup() {
        Stream.of(
                new Product("테스트1"),
                new Product("테스트2"),
                new Product("테스트3"),
                new Product("테스트4"),
                new Product("테스트5")
        ).forEach(repository::save);
    }

    @Test
    @Transactional
    public void findAll() {
        List<Product> productList = repository.findAll();

        Assertions.assertThat(productList)
                .hasSize(5)
                .extracting("name")
                .startsWith("테스트1")
                .endsWith("테스트4", "테스트5");


    }
}