package com.example.restdemo.dao;

import com.example.restdemo.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByPriceBetween(int from, int to);
    ProductEntity findByPriceAndName(int price, String name);
}
