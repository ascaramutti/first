package com.exam.first.service.mapper;

import com.exam.first.dto.ProductDto;
import com.exam.first.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductServiceMapper {

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "productName", expression = "java(product.getName().toUpperCase())")
    @Mapping(target = "productPrice", source = "price")
    ProductDto toProductDtoWithUpperCaseName (Product product);

    @Mapping(target = "productId", source = "id")
    @Mapping(target = "productName", source = "name")
    @Mapping(target = "productPrice", source = "price")
    ProductDto toProductDto (Product product);

}
