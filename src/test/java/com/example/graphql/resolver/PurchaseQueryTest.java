package com.example.graphql.resolver;

import com.example.graphql.dto.PurchaseDTO;
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
public class PurchaseQueryTest {

    @Autowired
    private PurchaseQuery query;

    @Test
    public void query_purchases() {
        List<PurchaseDTO> purchaseList = query.purchases();

        assertThat(purchaseList)
                .first()
                .hasFieldOrPropertyWithValue("product.name", "테스트1")
                .hasFieldOrPropertyWithValue("product.price", 1000)
                .hasFieldOrPropertyWithValue("user.name", "이정환")
                .hasFieldOrPropertyWithValue("user.age", 30)

        ;
        
    }

    @Test
    public void query_purchase_1() {
        PurchaseDTO purchase = query.purchase(1);

        assertThat(purchase)
                .hasFieldOrPropertyWithValue("product.name", "테스트1")
                .hasFieldOrPropertyWithValue("product.price", 1000)
                .hasFieldOrPropertyWithValue("user.name", "이정환")
                .hasFieldOrPropertyWithValue("user.age", 30)
        ;
    }
}