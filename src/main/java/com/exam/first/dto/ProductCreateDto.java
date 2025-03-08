package com.exam.first.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

    @NotNull(message = "El atributo productName NO puede ser nulo")
    private String productName;
    private Double price;
}
