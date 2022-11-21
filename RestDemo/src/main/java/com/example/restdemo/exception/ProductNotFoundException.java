package com.example.restdemo.exception;

public class ProductNotFoundException extends ProductException{
    public ProductNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
