package com.example.graphql.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table
@NoArgsConstructor
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private Product product;

    @OneToOne
    @JoinColumn(name = "id", nullable = false)
    private User user;

    public Purchase(Product product, User user, LocalDateTime date) {
        this.product = product;
        this.user = user;
        this.date = date;
    }

}
