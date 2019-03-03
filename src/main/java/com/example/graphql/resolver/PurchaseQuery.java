package com.example.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.example.graphql.dto.PurchaseDTO;
import com.example.graphql.entity.Purchase;
import com.example.graphql.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Transactional
public class PurchaseQuery implements GraphQLQueryResolver {
    private final PurchaseRepository purchaseRepository;

    public PurchaseQuery(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }

    public List<PurchaseDTO> purchases() {
        return purchaseRepository.findAll()
                .stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

    public PurchaseDTO purchase(long id) {
        Purchase purchase = purchaseRepository.findById(id).orElse(null);
        // lazy proxy call
        System.out.println(purchase.getUser());
        return new PurchaseDTO(purchase);
    }
}


