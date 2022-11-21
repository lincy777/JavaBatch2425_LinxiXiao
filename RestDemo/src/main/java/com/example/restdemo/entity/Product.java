package com.example.restdemo.entity;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class Product {
    private Long id;
    @NotNull
    private String name;
    @Max(99999)
    @Min(0)
    private Integer price;
    @Max(9999)
    @Min(0)
    private Integer stock;

    public Product() {

    }

    public Product(Long id, String name, Integer price, Integer stock) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Product(ProductEntity productEntity) {
        super();
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.stock = productEntity.getStock();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
