package com.example.graphql.repository;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository repository;


    // GraphqlApplication 에서 테스트 데이터 추가하도록 함.
//    @Before
//    public void setup() {
//        Stream.of(1, 2, 3, 4, 5)
//                .map(x -> new Product("테스트" + x, (x % 2) == 0 ? 500 : 1000))
//                .forEach(repository::save);
//    }

    @Test
    @Transactional
    public void findAll() {
        List<Product> productList = repository.findAll();

        Assertions.assertThat(productList)
                .hasSize(5)
                .extracting("name")
                .startsWith("테스트1")
                .endsWith("테스트4", "테스트5")
        ;


        Assertions.assertThat(productList)
                .hasSize(5)
                .extracting("price")
                .startsWith(1000, 500, 1000, 500, 1000)
        ;
    }
}