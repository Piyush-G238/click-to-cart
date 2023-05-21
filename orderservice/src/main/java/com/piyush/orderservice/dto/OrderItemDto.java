package com.piyush.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    private Long id;
    private Long productId;
    private Long quantity;
}
