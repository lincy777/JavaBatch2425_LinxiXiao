package com.example.restdemo.controller;

import com.example.restdemo.entity.Product;
import com.example.restdemo.exception.ProductNotFoundException;
import com.example.restdemo.service.Messages;
import com.example.restdemo.service.ProductService;
import com.example.restdemo.vo.ErrorResponse;
import com.example.restdemo.vo.PagedResponse;
import com.example.restdemo.vo.ResponseMessage;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ProductRestController {

    private static Logger logger = LoggerFactory.getLogger(ProductRestController.class);

    ProductService productService;
    Messages messages;

    @Autowired
    public ProductRestController(ProductService productService, Messages messages) {
        this.productService = productService;
        this.messages = messages;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        return "success";
    }

    @ApiOperation(value = "gets a single product")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getProduct(@PathVariable("id") long id) {
        Product product = productService.findById(id);

        if (product == null) {
            throw new ProductNotFoundException(messages.getMessage("PRODUCT_NOT_FOUND"));
        }

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @ApiOperation(value = "get product accordingly")
    @RequestMapping(value = "/product",  method = RequestMethod.GET)
    public ResponseEntity<PagedResponse<Product>> getUserPagenation(@RequestParam(required = false, defaultValue = "0") Integer pageNo,
                                                                 @RequestParam(required = false, defaultValue = "5") Integer rows,
                                                                 @RequestParam(required = false, defaultValue = "name") String orderBy) {

        PagedResponse<Product> products = productService.findPaginated(pageNo, rows, orderBy);

        if (products.isEmpty()) {
            throw new ProductNotFoundException(messages.getMessage("USER_NOT_FOUND"));
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /**
     * create a product
     *
     **/
    @ApiOperation(value = "create a product")
    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<ResponseMessage> createProduct(@Validated @RequestBody Product product, UriComponentsBuilder ucBuilder) {
        Product savedProduct = productService.saveProduct(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/product/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<>(new ResponseMessage(messages.getMessage("PRODUCT_CREATED"), savedProduct), headers, HttpStatus.CREATED);
    }

    /**
     * update a product
     *
     * @throws ProductNotFoundException
     **/
    @ApiOperation(value = "update a product")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product){
        Product currentProduct = productService.findById(id);

        if (currentProduct == null) {
            throw new ProductNotFoundException(messages.getMessage("PRODUCT_NOT_FOUND"));
        }

        currentProduct.setName(product.getName());
        currentProduct.setPrice(product.getPrice());
        currentProduct.setStock(product.getStock());

        productService.updateProduct(currentProduct);
        return new ResponseEntity<>(currentProduct, HttpStatus.OK);
    }

    /**
     * delete a product
     *
     * @throws ProductNotFoundException
     **/
    @ApiOperation(value = "delete a product")
    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable("id") long id) {

        Product product = productService.findById(id);
        if (product == null) {
            throw new ProductNotFoundException(messages.getMessage("PRODUCT_NOT_FOUND"));
        }
        productService.deleteProductById(id);
        return new ResponseEntity<>(new ResponseMessage(messages.getMessage("PRODUCT_DELETED"), product), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        logger.error("Controller Error",ex);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> productNotFound(Exception ex) {
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(HttpStatus.NOT_FOUND.value());
        error.setMessage(ex.getMessage());
        logger.error("Product Not Found Error",ex);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
