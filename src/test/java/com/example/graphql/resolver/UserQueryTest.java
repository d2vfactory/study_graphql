package com.example.graphql.resolver;

import com.example.graphql.entity.User;
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
public class UserQueryTest {

    @Autowired
    private UserQuery query;

    /*
        # User(id=1, name=이정환, age=30)
        # User(id=2, name=김동주, age=25)
        # User(id=3, name=송길주, age=20)
        # User(id=4, name=양충현, age=15)
    */

    @Test
    public void query_users_first() {
        List<User> userList = query.users();

        assertThat(userList)
                .hasSize(4)
                .first()
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "이정환")
                .hasFieldOrPropertyWithValue("age", 30)
        ;
    }

    @Test
    public void query_user_1to4() {
        assertThat(query.user(1))
                .hasFieldOrPropertyWithValue("id", 1l)
                .hasFieldOrPropertyWithValue("name", "이정환")
                .hasFieldOrPropertyWithValue("age", 30)
        ;

        assertThat(query.user(2))
                .hasFieldOrPropertyWithValue("id", 2l)
                .hasFieldOrPropertyWithValue("name", "김동주")
                .hasFieldOrPropertyWithValue("age", 25)
        ;

        assertThat(query.user(3))
                .hasFieldOrPropertyWithValue("id", 3l)
                .hasFieldOrPropertyWithValue("name", "송길주")
                .hasFieldOrPropertyWithValue("age", 20)
        ;

        assertThat(query.user(4))
                .hasFieldOrPropertyWithValue("id", 4l)
                .hasFieldOrPropertyWithValue("name", "양충현")
                .hasFieldOrPropertyWithValue("age", 15)
        ;

    }

    @Test
    public void query_countUsers_is4() {
        assertThat(query.countUsers()).isEqualTo(4);
    }
}