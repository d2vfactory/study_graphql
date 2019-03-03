package com.example.graphql.dto;

import com.example.graphql.entity.Product;
import com.example.graphql.entity.Purchase;
import com.example.graphql.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class PurchaseDTO {

    private long id;
    private String date;
    private Product product;
    private User user;

    public PurchaseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.product = purchase.getProduct();
        this.user = purchase.getUser();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.date = purchase.getDate().format(formatter);
    }


}
