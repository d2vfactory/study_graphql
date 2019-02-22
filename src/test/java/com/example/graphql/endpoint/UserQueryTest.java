package com.example.graphql.endpoint;

import com.example.graphql.entity.User;
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
public class UserQueryTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void users_name() throws JSONException, IOException {
        String query = "{users{name}}";

        ResponseEntity<String> responseEntity = callQueryGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<User> userList = jsonToList(result, "users", User.class);

        assertThat(userList)
                .extracting("name")
                .startsWith("이정환")
                .endsWith("양충현");

    }

    @Test
    public void users_name_age() throws JSONException, IOException {
        String query = "{users{name age}}";

        ResponseEntity<String> responseEntity = callQueryGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<User> userList = jsonToList(result, "users", User.class);

        assertThat(userList)
                .hasSize(4)
                .startsWith(
                        new User("이정환", 30),
                        new User("김동주", 25)
                )
                .endsWith(
                        new User("송길주", 20),
                        new User("양충현", 15)
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
