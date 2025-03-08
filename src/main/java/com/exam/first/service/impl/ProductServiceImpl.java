package com.exam.first.service.impl;

import com.exam.first.dto.ProductDto;
import com.exam.first.repository.ProductRepository;
import com.exam.first.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Flux<ProductDto> filterProductsByPrice(Double price) {
        log.info(">>> filterProductsByPrice start");
        return Flux.fromIterable(productRepository.findAll())
                .filter(product -> product.getPrice() > price)
                .map(product -> new ProductDto(product.getId(), product.getName(), product.getPrice()))
                .doOnTerminate(() -> log.info(">>> filterProductsByPrice end"));
    }
}
