package com.example.graphql.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.List;

@Slf4j
abstract public class AbstractGraphQLTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    protected ResponseEntity<String> callGraphql(String query) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);

        return callGraphQL(map);
    }

    protected ResponseEntity<String> callGraphql(String query, String variables) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);
        map.add("variables", variables);

        log.info("# map : {}", map);
        return callGraphQL(map);
    }

    protected ResponseEntity<String> callGraphql(String query, String operationName, String variables) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("query", query);
        map.add("operationName", operationName);
        map.add("variables", variables);

        return callGraphQL(map);
    }

    @NotNull
    private ResponseEntity<String> callGraphQL(MultiValueMap<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<String> responseEntity = restTemplate
                .exchange("/graphql", HttpMethod.POST, entity, String.class);

        log.info("header : {}", responseEntity.getHeaders());
        log.info("body : {}", responseEntity.getBody());

        return responseEntity;
    }

    protected JSONObject toJsonBody(ResponseEntity<String> responseEntity) throws JSONException {
        return new JSONObject(responseEntity.getBody()).getJSONObject("data");
    }

    protected <T> List<T> jsonToList(JSONObject jsonBody, String arrayKey, Class<T> clazz) throws IOException, JSONException {
        return toListObject(jsonBody.getJSONArray(arrayKey).toString(), clazz);
    }

    protected <T> T jsonToObject(JSONObject jsonBody, String key, Class<T> clazz) throws IOException, JSONException {
        return toObject(jsonBody.getJSONObject(key).toString(), clazz);
    }

    protected <T> List<T> toListObject(String jsonArray, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonArray, TypeFactory.defaultInstance().constructCollectionType(List.class, clazz));
    }

    protected <T> T toObject(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, TypeFactory.defaultInstance().constructType(clazz));
    }

}
