package com.example.restdemo.service;

import com.example.restdemo.dao.ProductRepository;
import com.example.restdemo.entity.Product;
import com.example.restdemo.entity.ProductEntity;
import com.example.restdemo.vo.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    public Product findById(long id) {
        ProductEntity productEntity = productRepo.findById(id).orElse(null);
        return productEntity == null ? null : new Product(productEntity);
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll().stream().map(Product::new).collect(Collectors.toList());
    }

    public Product saveProduct(Product product) {
        ProductEntity productEntity = productRepo.save(new ProductEntity(product));
        return new Product(productEntity);
    }

    public Product updateProduct(Product product) {
        ProductEntity productEntity = productRepo.saveAndFlush(new ProductEntity(product));
        return new Product(productEntity);
    }

    public void deleteProductById(long id) {
        productRepo.deleteById(id);
    }

    public PagedResponse<Product> findPaginated(int page, int size, String orderBy) {
        Sort sort = null;
        if (orderBy != null) {
            sort = Sort.by(Sort.Direction.ASC, orderBy);
        }
        Page<ProductEntity> page1 = productRepo.findAll(PageRequest.of(page, size, sort));
        List<ProductEntity> list = page1.getContent();
        PagedResponse<Product> result = new PagedResponse<>();
        result.setPage(page1.getNumber());
        result.setRows(page1.getSize());
        result.setTotalPage(page1.getTotalPages());
        result.setTotalElement(page1.getTotalElements());
        result.setOrder(page1.getSort().toString());
        result.setBody(list.stream().map(Product::new).collect(Collectors.toList()));
        return result;
    }
}
