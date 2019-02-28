package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.entity.User;
import com.example.graphql.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserQuery implements GraphQLQueryResolver {

    private final UserRepository userRepository;

    public UserQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> users() {
        return userRepository.findAll();
    }

    public User user(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public long countUsers() {
        return userRepository.count();
    }

}
