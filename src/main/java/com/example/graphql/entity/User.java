package com.example.graphql.entity;

import com.example.graphql.input.CreateUserInput;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public User(CreateUserInput createUserInput) {
        this.name = createUserInput.getName();
        this.age = createUserInput.getAge();
    }

}
