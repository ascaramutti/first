package com.exam.first.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(Long id) {
        super("El producto con ID "+ id + " no fue encontrado");
    }
}
