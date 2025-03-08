package com.exam.first.service.impl;

import com.exam.first.dto.ProductCreateDto;
import com.exam.first.dto.ProductDto;
import com.exam.first.exception.ProductNotFoundException;
import com.exam.first.repository.ProductRepository;
import com.exam.first.service.ProductService;
import com.exam.first.service.mapper.ProductServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductServiceMapper productServiceMapper;

    @Override
    public Flux<ProductDto> filterProductsByPrice(Double price) {
        log.info(">>> filterProductsByPrice start");
        return Flux.fromIterable(productRepository.findAll())
                .filter(product -> product.getPrice() > price)
                .map(productServiceMapper::toProductDtoWithUpperCaseName)
                .doOnTerminate(() -> log.info(">>> filterProductsByPrice end"));
    }

    @Override
    public Mono<ProductDto> findById(Long id) {
        log.info(">>> findById start");
        return Mono.fromCallable(() -> productRepository.findById(id))
                .flatMap(optionalProduct -> optionalProduct
                        .map(product -> Mono.just(productServiceMapper.toProductDto(product)))
                        .orElseThrow(() -> new ProductNotFoundException(id)))
                .doOnTerminate(() -> log.info(">>> findById end"));
    }

    @Override
    public Mono<ProductDto> createProduct(ProductCreateDto productCreateDto) {
        log.info(">>> createProduct start");
        return Mono.just(productCreateDto)
                .map(productServiceMapper::toProduct)
                .map(productRepository::save)
                .map(productServiceMapper::toProductDtoWithUpperCaseName)
                .doOnTerminate(() -> log.info(">>> createProduct end"));
    }
}
