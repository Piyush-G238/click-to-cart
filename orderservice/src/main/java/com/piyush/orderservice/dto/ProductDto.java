package com.piyush.orderservice.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Double price;

    public ProductDto() {
    }
}
