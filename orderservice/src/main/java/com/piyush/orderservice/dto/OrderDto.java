package com.piyush.orderservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class OrderDto {
    private Long id;
    private Set<OrderItemDto> orderItems;
    private Double price;
}
