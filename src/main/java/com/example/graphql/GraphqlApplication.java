package com.example.graphql;

import com.example.graphql.entity.Product;
import com.example.graphql.entity.Purchase;
import com.example.graphql.entity.User;
import com.example.graphql.repository.ProductRepository;
import com.example.graphql.repository.PurchaseRepository;
import com.example.graphql.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootApplication
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData(
            ProductRepository productRepository,
            UserRepository userRepository,
            PurchaseRepository purchaseRepository) {

        return args -> {
             /*
                # User(id=1, name=이정환, age=30)
                # User(id=2, name=김동주, age=25)
                # User(id=3, name=송길주, age=20)
                # User(id=4, name=양충현, age=15)

                # Product(id=1, name=테스트1, price=1000)
                # Product(id=2, name=테스트2, price=500)
                # Product(id=3, name=테스트3, price=1000)
                # Product(id=4, name=테스트4, price=500)
                # Product(id=5, name=테스트5, price=1000)

                # purchase(id=1, product_id=1, user_id=1, date)
                # # -> purchase(id=1, product(id=1, ~~~), user(id=1, ~~~), date)
             */

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


            // purchase test date
            Product product1 = productRepository.findById(1l).get();
            User user1 = userRepository.findById(1l).get();

            Purchase purchase1 = new Purchase(product1, user1, LocalDateTime.now());
            purchaseRepository.save(purchase1);

        };
    }


}

