package com.piyush.orderservice.service;

import com.piyush.orderservice.dto.InventoryDto;
import com.piyush.orderservice.dto.OrderDto;
import com.piyush.orderservice.dto.OrderItemDto;
import com.piyush.orderservice.dto.ProductDto;
import com.piyush.orderservice.model.Order;
import com.piyush.orderservice.model.OrderItem;
import com.piyush.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private RestTemplate template;

    public Map<String, String> createOrder(OrderDto orderDto) {
        Order order = toOrderEntity(orderDto);
        Set<OrderItem> orderItems = order.getOrderedItems();
        boolean orderValid = validateOrder(order.getOrderedItems());
        if (orderValid) {
            orderItems.forEach(item -> item.setOrder(order));
            Double orderPrice = calculateOrderPrice(orderItems);
            order.setPrice(orderPrice);
            repository.save(order);
            updateInventory(orderItems);
            return createResponse("Your order has been placed successfully. Order ID: "+ order.getId(),"201 CREATED");
        }
        return null;
    }

    public OrderDto getOrderById(Long id) {
        Order order = repository.findById(id).orElseThrow();
        return toOrderDto(order);
    }
    private void updateInventory(Set<OrderItem> orderItems) {
        for (OrderItem itm:orderItems) {
            ResponseEntity<InventoryDto> response = template.getForEntity("http://inventory-service/api/v1/inventories/{productId}", InventoryDto.class, itm.getProductId());
            InventoryDto dto = response.getBody();
            assert dto != null;
            Map<String, Long> map = Map.of("qty", (dto.getQuantity()- itm.getQuantity()));
            template.put("http://inventory-service/api/v1/inventories/{productId}", map, itm.getProductId());
        }
    }

    public Map<String, String> deleteOrderById(Long id){
        Order order = repository.findById(id).orElseThrow(() -> new RuntimeException(""));
        repository.delete(order);
        return createResponse("Your Order has been cancelled successfully", "200 OK");
    }

    private Double calculateOrderPrice(Set<OrderItem> orderItems) {
        Double price = 0.0;
        for (OrderItem item: orderItems) {
            ResponseEntity<ProductDto> response = template.getForEntity("http://product-service/api/v1/products/{id}", ProductDto.class, item.getProductId());
            ProductDto dto = response.getBody();
            assert dto != null;
            price += (item.getQuantity() * dto.getPrice());
        }
        return price;
    }

    private boolean validateOrder(Set<OrderItem> orderedItems) {
        for (OrderItem item: orderedItems) {
            ResponseEntity<InventoryDto> response = template.getForEntity("http://inventory-service/api/v1/inventories/{productId}", InventoryDto.class, item.getProductId());
            if (response.getStatusCode().is4xxClientError()){
                throw new RuntimeException("Product having ID: "+ item.getProductId()+" is not available in application");
            }
            InventoryDto dto = response.getBody();
            assert dto != null;
            if (item.getQuantity() > dto.getQuantity()){
                throw new RuntimeException("OrderItem having a product with ID: "+item.getProductId()+" is exceeding its ordering quantity.");
            }
        }
        return true;
    }

    private Order toOrderEntity(OrderDto orderDto) {
        return Order.builder()
                .orderedItems(orderDto
                        .getOrderItems()
                        .stream()
                        .map(this::toOrderItemEntity)
                        .collect(Collectors.toSet()))
                .build();
    }

    private OrderItem toOrderItemEntity(OrderItemDto itemDto) {
        return OrderItem.builder()
                .productId(itemDto.getProductId())
                .quantity(itemDto.getQuantity())
                .build();
    }

    public OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .orderItems(order
                        .getOrderedItems()
                        .stream()
                        .map(this::toItemDto)
                        .collect(Collectors.toSet()))
                .price(order.getPrice())
                .build();
    }

    public OrderItemDto toItemDto(OrderItem orderItem) {
        return OrderItemDto.builder()
                .id(orderItem.getId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .build();
    }

    private Map<String, String> createResponse(String msg, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("message", msg);
        map.put("status", status);
        map.put("timestamp", LocalDateTime.now().toString());
        return map;
    }
}
