package com.example.graphql.endpoint;

import com.example.graphql.entity.User;
import com.example.graphql.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserGraphQLTest extends AbstractGraphQLTest {

    @Autowired
    private UserRepository repository;


    @Before
    public void setup() {
        List<User> userList = repository.findAll();
        log.info("# Test Data Set");
        userList.stream()
                .forEach(x -> log.info("# {}", x));

    }


    @Test
    public void query_users_name() throws JSONException, IOException {
        String query = "{users{name}}";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        List<User> userList = jsonToList(result, "users", User.class);

        assertThat(userList)
                .extracting("name")
                .startsWith("이정환")
                .endsWith("양충현");

    }


    @Test
    public void query_users_name_age() throws JSONException, IOException {
        String query = "{users{name age}}";

        ResponseEntity<String> responseEntity = callGraphql(query);

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


    @Test
    public void query_user_1() throws JSONException, IOException {
        String query = "{user(id:1) {id name age}}";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        User user = jsonToObject(result, "user", User.class);

        assertThat(user)
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "이정환")
                .hasFieldOrPropertyWithValue("age", 30);
    }

    @Test
    public void mutation_newUser() throws JSONException, IOException {
        String query = new StringBuilder()
                .append("mutation {")
                .append("   newUser(name:\"윤호영\", age:28) {")
                .append("           id")
                .append("           name")
                .append("           age")
                .append("   }")
                .append("}")
                .toString();

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        User user = jsonToObject(result, "newUser", User.class);

        assertThat(user)
                .hasFieldOrPropertyWithValue("name", "윤호영")
                .hasFieldOrPropertyWithValue("age", 28);
    }

    @Test
    public void mutation_createUser_variables() throws JSONException, IOException {
        String query = new StringBuilder()
                .append("mutation createUser($input: CreateUserInput!) {")
                .append("   createUser(input:$input) {id, name, age}")
                .append("}")
                .toString();

        String variable = new StringBuilder()
                .append("{")
                .append("   'input' : {")
                .append("       'name' : '윤호영', ")
                .append("       'age' : 28 ")
                .append("   }")
                .append("}")
                .toString().replaceAll("'", "\"");

        ResponseEntity<String> responseEntity = callGraphql(query, variable);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        User user = jsonToObject(result, "createUser", User.class);

        assertThat(user)
                .hasFieldOrPropertyWithValue("name", "윤호영")
                .hasFieldOrPropertyWithValue("age", 28);
    }

    @Test
    public void mutation_deleteUser_1() throws JSONException {
        String query = "mutation {deleteUser(id:1) }";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

        JSONObject result = toJsonBody(responseEntity);

        assertThat(result.getBoolean("deleteUser")).isTrue();
    }

    @Test
    public void mutation_updateUserName_1() throws JSONException, IOException {
        String query = "mutation {updateUserName(id:1, name:'박철') {id, name, age} }";

        query = query.replaceAll("'", "\"");

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        User user = jsonToObject(result, "updateUserName", User.class);

        assertThat(user)
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "박철")
                .hasFieldOrPropertyWithValue("age", 30);
    }

    @Test
    public void mutation_updateUserAge_1() throws JSONException, IOException {
        String query = "mutation {updateUserAge(id:1, age:35) {id, name, age} }";

        ResponseEntity<String> responseEntity = callGraphql(query);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);


        JSONObject result = toJsonBody(responseEntity);

        User user = jsonToObject(result, "updateUserAge", User.class);

        assertThat(user)
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "이정환")
                .hasFieldOrPropertyWithValue("age", 35);
    }

}
