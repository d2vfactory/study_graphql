package com.example.graphql;

import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import com.example.graphql.resolver.Mutation;
import com.example.graphql.resolver.Query;
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
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args ->
                Stream.of(1, 2, 3, 4, 5)
                        .map(x -> new Product("테스트" + x, (x % 2) == 0 ? 500 : 1000))
                        .forEach(productRepository::save);

    }


    @Bean
    public Query query(ProductRepository productRepository) {
        return new Query(productRepository);
    }

    @Bean
    public Mutation mutation(ProductRepository productRepository) {
        return new Mutation(productRepository);
    }

}

