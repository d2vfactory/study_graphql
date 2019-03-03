package com.example.graphql.endpoint;

import com.example.graphql.dto.PurchaseDTO;
import com.example.graphql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PurchaseGraphQLTest extends AbstractGraphQLTest {

    @Test
    public void query_purchases() throws JSONException, IOException {
        String query = new StringBuilder()
                .append("{")
                .append("   purchases {")
                .append("           product {name, price}")
                .append("           user {name}")
                .append("           date")
                .append("   }")
                .append("}")
                .toString();

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        List<PurchaseDTO> purchaseList = jsonToList(result, "purchases", PurchaseDTO.class);

        assertThat(purchaseList)
                .first()
                .hasFieldOrPropertyWithValue("product.name", "테스트1")
                .hasFieldOrPropertyWithValue("product.price", 1000)
                .hasFieldOrPropertyWithValue("user.name", "이정환")

        ;
    }

    @Test
    public void query_purchase_1() throws JSONException, IOException {
        String query = new StringBuilder()
                .append("{")
                .append("   purchase(id:1) {")
                .append("           product {name}")
                .append("           user {name age}")
                .append("           date")
                .append("   }")
                .append("}")
                .toString();

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        PurchaseDTO purchase = jsonToObject(result, "purchase", PurchaseDTO.class);

        assertThat(purchase)
                .hasFieldOrPropertyWithValue("product.name", "테스트1")
                .hasFieldOrPropertyWithValue("user.name", "이정환")
                .hasFieldOrPropertyWithValue("user.age", 30)
        ;
    }

    @Test
    public void mutation_newPurchase() throws JSONException, IOException {
        String query = new StringBuilder()
                .append("mutation {")
                .append("   newPurchase(productId:3, userId:4) {")
                .append("           product {id name} ")
                .append("           user {id name} ")
                .append("   }")
                .append("}")
                .toString();

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        PurchaseDTO purchase = jsonToObject(result, "newPurchase", PurchaseDTO.class);

        assertThat(purchase)
                .hasFieldOrPropertyWithValue("product.id", 3l)
                .hasFieldOrPropertyWithValue("product.name", "테스트3")
                .hasFieldOrPropertyWithValue("user.id", 4l)
                .hasFieldOrPropertyWithValue("user.name", "양충현")
        ;

    }
}
