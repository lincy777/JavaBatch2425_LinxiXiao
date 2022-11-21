package com.example.restdemo.service;

import com.example.restdemo.entity.Product;
import com.example.restdemo.vo.PagedResponse;

import java.util.List;

public interface ProductService {
    Product findById(long id);

    Product saveProduct(Product product);

    Product updateProduct(Product product);

    void deleteProductById(long id);

    List<Product> findAllProducts();

    PagedResponse<Product> findPaginated(int page, int size, String orderBy);
}
