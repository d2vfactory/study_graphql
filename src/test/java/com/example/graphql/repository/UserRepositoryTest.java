package com.example.graphql.repository;

import com.example.graphql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

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
    public void findAll() {
        List<User> userList = repository.findAll();

        assertThat(userList)
                .hasSize(4)
                .extracting("age")
                .startsWith(30, 25, 20, 15)
        ;
    }


}