package com.example.graphql.endpoint;

import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GraphqlEndPointTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        Stream.of(
                new Product("테스트1"),
                new Product("테스트2"),
                new Product("테스트3"),
                new Product("테스트4"),
                new Product("테스트5")
        ).forEach(productRepository::save);
    }

    @Test
    public void getSchema() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/graphql/schema.json", String.class);
        log.info("header : {}", responseEntity.getHeaders());
        log.info("body : {}", responseEntity.getBody());

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }


    @Test
    public void getProductsNames() throws JSONException, IOException {
        String query = "{products{name}}";

        ResponseEntity<String> responseEntity = callQueryGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<Product> productList = jsonToList(result, "products", Product.class);

        assertThat(productList)
                .extracting("name")
                .startsWith("테스트1")
                .endsWith("테스트5");

    }

    private ResponseEntity<String> callQueryGraphql(String query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange("/graphql", HttpMethod.POST, entity, String.class);

        log.info("header : {}", responseEntity.getHeaders());
        log.info("body : {}", responseEntity.getBody());

        return responseEntity;
    }

    private JSONObject toJsonBody(ResponseEntity<String> responseEntity) throws JSONException {
        return new JSONObject(responseEntity.getBody()).getJSONObject("data");
    }

    private <T> List<T> toListObject(String jsonArray, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonArray, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }

    private <T> List<T> jsonToList(JSONObject jsonBody, String arrayKey, Class<T> clazz) throws IOException, JSONException {
        return toListObject(jsonBody.getJSONArray(arrayKey).toString(), clazz);
    }


}
