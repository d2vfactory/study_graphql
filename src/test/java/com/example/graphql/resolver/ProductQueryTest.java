package com.example.graphql.resolver;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductQueryTest {

    @Autowired
    private ProductQuery query;

    /*
        # Product(id=1, name=테스트1, price=1000)
        # Product(id=2, name=테스트2, price=500)
        # Product(id=3, name=테스트3, price=1000)
        # Product(id=4, name=테스트4, price=500)
        # Product(id=5, name=테스트5, price=1000)
    */

    @Test
    public void query_products_first() {
        List<Product> productList = query.products();

        assertThat(productList)
                .hasSize(5)
                .first()
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "테스트1")
                .hasFieldOrPropertyWithValue("price", 1000)
        ;
    }

    @Test
    public void query_product_1to5() {
        assertThat(query.product(1))
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "테스트1")
                .hasFieldOrPropertyWithValue("price", 1000)
        ;

        assertThat(query.product(2))
                .hasFieldOrPropertyWithValue("id", 2l)
                .hasFieldOrPropertyWithValue("name", "테스트2")
                .hasFieldOrPropertyWithValue("price", 500)
        ;

        assertThat(query.product(3))
                .hasFieldOrPropertyWithValue("id", 3l)
                .hasFieldOrPropertyWithValue("name", "테스트3")
                .hasFieldOrPropertyWithValue("price", 1000)
        ;

        assertThat(query.product(4))
                .hasFieldOrPropertyWithValue("id", 4l)
                .hasFieldOrPropertyWithValue("name", "테스트4")
                .hasFieldOrPropertyWithValue("price", 500)
        ;

        assertThat(query.product(5))
                .hasFieldOrPropertyWithValue("id", 5l)
                .hasFieldOrPropertyWithValue("name", "테스트5")
                .hasFieldOrPropertyWithValue("price", 1000)
        ;

    }

    @Test
    public void query_countProducts_is5() {
        assertThat(query.countProducts()).isEqualTo(5);
    }

}