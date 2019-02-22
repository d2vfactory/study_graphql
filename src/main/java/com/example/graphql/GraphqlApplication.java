package com.example.graphql;

import com.example.graphql.entity.Product;
import com.example.graphql.entity.User;
import com.example.graphql.repository.ProductRepository;
import com.example.graphql.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(
            ProductRepository productRepository,
            UserRepository userRepository) {

        return args -> {
            // product test data
            Stream.of(1, 2, 3, 4, 5)
                    .map(x -> new Product("테스트" + x, (x % 2) == 0 ? 500 : 1000))
                    .forEach(productRepository::save);

            // user test data
            Stream.of(
                    new User("이정환", 30),
                    new User("김동주", 25),
                    new User("송길주", 20),
                    new User("양충현", 15)
            ).forEach(userRepository::save);

        };
    }


}

