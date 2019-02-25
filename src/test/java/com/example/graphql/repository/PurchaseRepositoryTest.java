package com.example.graphql.repository;

import com.example.graphql.entity.Product;
import com.example.graphql.entity.Purchase;
import com.example.graphql.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@Slf4j
@RunWith(SpringRunner.class)
@DataJpaTest
public class PurchaseRepositoryTest {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;


    private List<User> testUserList;
    private List<Product> testProductList;

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
     */
    @Before
    public void setup() {
        log.info("# Test Data Set");
        testUserList = userRepository.findAll();
        testProductList = productRepository.findAll();

        testUserList.stream()
                .forEach(x -> log.info("# {}", x));

        testProductList.stream()
                .forEach(x -> log.info("# {}", x));

    }

    @Test
    @Transactional
    public void save_findAll() {
        Random rand = new Random();

        purchaseRepository.save(
                new Purchase(
                        testProductList.get(rand.nextInt(testProductList.size())),
                        testUserList.get(0), // 이정환
                        LocalDateTime.now().minusHours(2))
        );

        purchaseRepository.save(
                new Purchase(
                        testProductList.get(rand.nextInt(testProductList.size())),
                        testUserList.get(1), // 김동주
                        LocalDateTime.now().minusHours(1))
        );

        purchaseRepository.save(
                new Purchase(
                        testProductList.get(rand.nextInt(testProductList.size())),
                        testUserList.get(2), // 송길주
                        LocalDateTime.now().minusMinutes(20))
        );

        purchaseRepository.save(
                new Purchase(
                        testProductList.get(rand.nextInt(testProductList.size())),
                        testUserList.get(3), // 양충현
                        LocalDateTime.now())
        );


        List<Purchase> purchaseList = purchaseRepository.findAll();

        assertThat(purchaseList)
                .hasSize(4)
                .extracting("user")
                .extracting("name")
                .contains("이정환", "송길주", "김동주", "양충현");
    }

    @Test
    @Transactional
    public void update_product1_product2() {
        purchaseRepository.save(
                new Purchase(
                        testProductList.get(0),
                        testUserList.get(2), // 송길주
                        LocalDateTime.now())
        );


        Purchase purchase = purchaseRepository.findById(1l).get();

        assertThat(purchase)
                .extracting("product")
                .extracting("name", "price")
                .contains(tuple("테스트1", 1000))
        ;

        assertThat(purchase)
                .extracting("user")
                .extracting("name", "age")
                .contains(tuple("송길주", 20))
        ;


        // product 변경 : 테스트1 -> 테스트2
        purchase.setProduct(testProductList.get(1));
        purchaseRepository.save(purchase);


        assertThat(purchase)
                .extracting("product")
                .extracting("name", "price")
                .contains(tuple("테스트2", 500))
        ;

        assertThat(purchase)
                .extracting("user")
                .extracting("name", "age")
                .contains(tuple("송길주", 20))
        ;
    }

    @Test
    @Transactional
    public void update_userAge20_userAge10() {
        purchaseRepository.save(
                new Purchase(
                        testProductList.get(0),
                        testUserList.get(2), // 송길주
                        LocalDateTime.now())
        );


        Purchase purchase = purchaseRepository.findById(1l).get();

        assertThat(purchase)
                .extracting("user")
                .extracting("name", "age")
                .contains(tuple("송길주", 20))
        ;


        // user의 age 변경 : 송길주, 20 -> 10
        User user = userRepository.findById(3l).get();
        user.setAge(10);
        userRepository.save(user);


        assertThat(purchase)
                .extracting("user")
                .extracting("name", "age")
                .contains(tuple("송길주", 10))
        ;

    }


}