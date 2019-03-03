package com.example.graphql.repository;

import com.example.graphql.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 여러값을 조회할 경우, lazy type이 매핑되었을 경우, fetch join으로 한번에 가져오도록 처리.
    @Override
    @Query("select distinct p from Purchase p join fetch p.product join fetch p.user")
    List<Purchase> findAll();
}
