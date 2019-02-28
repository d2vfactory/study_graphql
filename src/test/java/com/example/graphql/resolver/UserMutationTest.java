package com.example.graphql.resolver;

import com.example.graphql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserMutationTest {

    @Autowired
    private UserMutation mutation;

    @Autowired
    private UserQuery query;

    @Test
    public void newUser_윤호영_28() {
        mutation.newUser("윤호영", 28);

        List<User> userList = query.users();

        assertThat(userList)
                .filteredOn("name", "윤호영")
                .anyMatch(x -> x.getAge() == 28);

    }

    @Test
    public void delete_송길주() {
        List<User> userList = query.users();

        User user = userList.stream()
                .filter(x -> x.getName().equals("송길주"))
                .collect(Collectors.toList()).get(0);

        mutation.deleteUser(user.getId());

        userList = query.users();

        assertThat(userList)
                .noneMatch(x -> x.getName().equals("송길주"));

    }

    @Test
    public void updateAge_송길주_20to15() {
        List<User> userList = query.users();

        User user = userList.stream()
                .filter(x -> x.getName().equals("송길주"))
                .collect(Collectors.toList()).get(0);

        assertThat(user.getAge()).isEqualTo(20);

        user = mutation.updateUserAge(user.getId(), 15);

        assertThat(user.getAge()).isEqualTo(15);

    }

    @Test
    public void updateName_송길주() {
        List<User> userList = query.users();

        User user = userList.stream()
                .filter(x -> x.getName().equals("송길주"))
                .collect(Collectors.toList()).get(0);

        assertThat(user.getName()).isEqualTo("송길주");

        user = mutation.updateUserName(user.getId(), "김길주");

        assertThat(user.getName()).isEqualTo("김길주");

    }
}