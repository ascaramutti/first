package com.exam.first.controller;

import com.exam.first.dto.ProductCreateDto;
import com.exam.first.dto.ProductDto;
import com.exam.first.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> findProductById(
            @PathVariable("id") Long id) {

        log.info(">>> findProductById start");

        return productService.findById(id)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .doOnTerminate(() -> log.info(">>> findProductById end"));
    }

    @PostMapping
    public Mono<ResponseEntity<ProductDto>> newProduct(
            @Valid @RequestBody ProductCreateDto productCreateDto) {
        log.info(">>> newProduct start");

        return productService.createProduct(productCreateDto)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .doOnTerminate(() -> log.info(">>> newProduct end"));
    }

    @GetMapping(value = "/delayed-message", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<String> getDelayedMessage() {
        return Flux.concat(
                Mono.delay(Duration.ofSeconds(5))
                        .map(i -> "{\"message\": \"Hola después de 3 segundos!\"}"),
                Mono.delay(Duration.ofSeconds(10))
                        .map(i -> "{\"message\": \"Este es un segundo mensaje!\"}")
        );
    }

}
