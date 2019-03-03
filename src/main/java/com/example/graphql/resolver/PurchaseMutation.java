package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.graphql.dto.PurchaseDTO;
import com.example.graphql.entity.Product;
import com.example.graphql.entity.Purchase;
import com.example.graphql.entity.User;
import com.example.graphql.repository.ProductRepository;
import com.example.graphql.repository.PurchaseRepository;
import com.example.graphql.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PurchaseMutation implements GraphQLMutationResolver {

    private final PurchaseRepository purchaseRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public PurchaseMutation(PurchaseRepository purchaseRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.purchaseRepository = purchaseRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public PurchaseDTO newPurchase(long productId, long userId) {
        Product product = productRepository.findById(productId).get();
        User user = userRepository.findById(userId).get();

        Purchase purchase = new Purchase(product, user, LocalDateTime.now());
        purchaseRepository.save(purchase);
        return new PurchaseDTO(purchase);
    }


}
