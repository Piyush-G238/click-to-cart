package com.piyush.orderservice.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
public class InventoryDto {
    private Long id;
    private String name;
    private Long productId;
    private Long quantity;

    public InventoryDto() {
    }
}
