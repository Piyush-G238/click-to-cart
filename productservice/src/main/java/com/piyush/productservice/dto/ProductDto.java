package com.piyush.productservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDto {
    private Long id;

    @NotEmpty(message = "Name for product cannot be empty")
    private String name;

    @NotEmpty(message = "Description for product cannot be empty")
    private String description;

    @Min(value = 100, message = "Minimum cost of product should be Rs.100")
    private Double price;
}
