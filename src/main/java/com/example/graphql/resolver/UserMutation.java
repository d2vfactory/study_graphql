package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.entity.User;
import com.example.graphql.input.CreateUserInput;
import com.example.graphql.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserMutation implements GraphQLMutationResolver {

    private final UserRepository userRepository;

    public UserMutation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User newUser(String name, int age) {
        User user = new User(name, age);
        userRepository.save(user);
        return user;
    }

    public User createUser(CreateUserInput createUserInput) {
        User user = new User(createUserInput);
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
