package com.example.graphql.endpoint;

import com.example.graphql.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
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

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductQueryTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

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

        ResponseEntity<String> responseEntity = callQueryGraphql(query);

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

        ResponseEntity<String> responseEntity = callQueryGraphql(query);

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

    private <T> List<T> jsonToList(JSONObject jsonBody, String arrayKey, Class<T> clazz) throws IOException, JSONException {
        return toListObject(jsonBody.getJSONArray(arrayKey).toString(), clazz);
    }

    private <T> T jsonToObject(JSONObject jsonBody, String key, Class<T> clazz) throws IOException, JSONException {
        return toObject(jsonBody.getJSONObject(key).toString(), clazz);
    }

    private <T> List<T> toListObject(String jsonArray, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonArray, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }

    private <T> T toObject(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, TypeFactory.defaultInstance().constructType(clazz));
    }


}
