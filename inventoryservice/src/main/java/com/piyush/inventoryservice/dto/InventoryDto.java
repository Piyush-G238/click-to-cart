package com.piyush.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InventoryDto {
    private Long id;
    private String name;
    private Long productId;
    private Long quantity;
}
