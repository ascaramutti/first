package com.exam.first.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

    @NotNull(message = "El atributo productName NO puede ser nulo")
    private String productName;
    private Double price;
}
