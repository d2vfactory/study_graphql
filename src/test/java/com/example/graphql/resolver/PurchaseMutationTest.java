package com.example.graphql.resolver;

import com.example.graphql.dto.PurchaseDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PurchaseMutationTest {

    @Autowired
    private PurchaseMutation mutation;

    @Autowired
    private PurchaseQuery query;

    @Test
    public void newPurchase() {
        mutation.newPurchase(2, 3);

        PurchaseDTO newPurchase = query.purchases()
                .stream()
                .reduce((first, second) -> second)
                .get();


        assertThat(newPurchase)
                .hasFieldOrPropertyWithValue("product.name", "테스트2")
                .hasFieldOrPropertyWithValue("product.price", 500)
                .hasFieldOrPropertyWithValue("user.name", "송길주")
        ;

    }
}