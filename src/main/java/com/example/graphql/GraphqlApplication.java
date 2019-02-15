package com.example.graphql;

import com.example.graphql.entity.Product;
import com.example.graphql.repository.ProductRepository;
import com.example.graphql.resolver.Mutation;
import com.example.graphql.resolver.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GraphqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphqlApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(ProductRepository productRepository){
		return args -> {
			productRepository.save(new Product("테스트1"));
			productRepository.save(new Product("테스트2"));
			productRepository.save(new Product("테스트3"));
			productRepository.save(new Product("테스트4"));
			productRepository.save(new Product("테스트5"));

		};
	}


	@Bean
	public Query query(ProductRepository productRepository){
		return new Query(productRepository);
	}

	@Bean
	public Mutation mutation(ProductRepository productRepository){
		return new Mutation(productRepository);
	}

}

