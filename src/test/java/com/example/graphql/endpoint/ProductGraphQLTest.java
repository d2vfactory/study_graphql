package com.example.graphql.endpoint;

import com.example.graphql.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductGraphQLTest extends AbstractGraphQLTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getSchema() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/graphql/schema.json", String.class);
        log.info("header : {}", responseEntity.getHeaders());
        log.info("body : {}", responseEntity.getBody());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    public void products_name() throws JSONException, IOException {
        String query = "{products{name}}";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<Product> productList = jsonToList(result, "products", Product.class);

        assertThat(productList)
                .extracting("name")
                .startsWith("테스트1")
                .endsWith("테스트5");

    }

    @Test
    public void products_name_price() throws JSONException, IOException {
        String query = "{products{name price}}";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<Product> productList = jsonToList(result, "products", Product.class);

        assertThat(productList)
                .hasSize(5)
                .startsWith(
                        new Product("테스트1", 1000),
                        new Product("테스트2", 500)
                )
                .endsWith(
                        new Product("테스트4", 500),
                        new Product("테스트5", 1000)
                )
        ;
    }

}
