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



    public User newUser(String name, int age) {
        User user = new User(name, age);
        userRepository.save(user);
        return user;
    }

    public boolean deleteUser(long id) {
        userRepository.deleteById(id);
        return true;
    }

    public User updateUserName(long id, String name) {
        User user = userRepository.findById(id).orElse(null);
        user.setName(name);
        userRepository.save(user);
        return user;
    }

    public User updateUserAge(long id, int age) {
        User user = userRepository.findById(id).orElse(null);
        user.setAge(age);
        userRepository.save(user);
        return user;
    }


}
