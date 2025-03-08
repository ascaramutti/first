package com.exam.first.controller;

import com.exam.first.dto.ProductDto;
import com.exam.first.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping("/filter/{minPrice}")
    public Mono<ResponseEntity<Flux<ProductDto>>> filterProducts(
            @PathVariable("minPrice") Double minPrice) {

        log.info(">>> filterProducts start");

        Flux<ProductDto> products = productService.filterProductsByPrice(minPrice);

        return products
                .hasElements()
                .filter(Boolean::booleanValue)
                .flatMap(e -> Mono.just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(products)))
                .switchIfEmpty(Mono.just(ResponseEntity.noContent().build()))
                .doOnTerminate(() -> log.info(">>> filterProducts end"));
    }

    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<ProductDto>> findProductById(
            @PathVariable("id") Long id) {

        log.info(">>> findProductById start");

        return productService.findById(id)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .doOnTerminate(() -> log.info(">>> findProductById end"));
    }

}
