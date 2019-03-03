package com.example.graphql.resolver;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductMutationTest {

    @Autowired
    private ProductMutation mutation;

    @Autowired
    private ProductQuery query;

    @Test
    public void newProduct_GraphQL_price10(){
        mutation.newProduct("GraphQL", 10);

        Product product = query.products()
                .stream()
                .filter(x->x.getName().equals("GraphQL"))
                .collect(Collectors.toList()).get(0);

        assertThat(product)
                .hasFieldOrPropertyWithValue("name","GraphQL")
                .hasFieldOrPropertyWithValue("price", 10);

    }


    @Test
    public void delete_테스트5(){
        Product product = query.product(5l);
        assertThat(product.getName()).isEqualTo("테스트5");

        boolean result = mutation.deleteProduct(5l);
        assertThat(result).isTrue();

        product = query.product(5l);
        assertThat(product).isNull();
    }


    @Test
    public void updateProductName_테스트2to샘플2(){

        Product product = query.products()
                .stream()
                .filter(x->x.getName().equals("테스트2"))
                .collect(Collectors.toList()).get(0);

        mutation.updateProductName(product.getId(), "샘플2");

        product = query.product(product.getId());

        assertThat(product.getName()).isEqualTo("샘플2");
    }


}