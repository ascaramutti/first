package com.exam.first.service;

import com.exam.first.dto.ProductDto;
import reactor.core.publisher.Flux;

public interface ProductService {

    Flux<ProductDto> filterProductsByPrice(Double price);

}
