package com.exam.first.service;

import com.exam.first.dto.ProductCreateDto;
import com.exam.first.dto.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<ProductDto> filterProductsByPrice(Double price);

    Mono<ProductDto> findById(Long id);

    Mono<ProductDto> createProduct(ProductCreateDto productCreateDto);

}
