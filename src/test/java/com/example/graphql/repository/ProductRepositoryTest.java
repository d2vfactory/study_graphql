package com.example.graphql.repository;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;


    @Before
    public void setup() {
        List<Product> productList = repository.findAll();
        log.info("# Test Data Set");
        productList.stream()
                .forEach(x -> log.info("# {}", x));

    }

    @Test
    public void findAll() {
        List<Product> productList = repository.findAll();

        assertThat(productList)
                .hasSize(5)
                .extracting("name")
                .startsWith("테스트1")
                .endsWith("테스트4", "테스트5")
        ;


        assertThat(productList)
                .hasSize(5)
                .extracting("price")
                .startsWith(1000, 500, 1000, 500, 1000)
        ;
    }

    @Test
    public void update_테스트1_price500() {
        Product test1 = repository.findById(1L).get();
        assertThat(test1.getPrice()).isEqualTo(1000);

        test1.setPrice(500);
        repository.save(test1);

        test1 = repository.findById(1L).get();
        assertThat(test1.getPrice()).isEqualTo(500);
    }
}