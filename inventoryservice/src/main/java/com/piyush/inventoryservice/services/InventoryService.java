package com.piyush.inventoryservice.services;

import com.piyush.inventoryservice.dto.InventoryDto;
import com.piyush.inventoryservice.exception.ProductNotFoundException;
import com.piyush.inventoryservice.model.Inventory;
import com.piyush.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public Map<String, String> createInventory(InventoryDto dto) {
        Boolean productAvailable = restTemplate.getForObject("http://localhost:8080/api/v1/products/id/{productId}", Boolean.class,dto.getProductId());
        if (productAvailable) {
            boolean inventoryAvailable = repository.existsByProductId(dto.getProductId());
            if (!inventoryAvailable){
                Inventory inventory =toEntity(dto);
                repository.save(inventory);
                return createResponse("Inventory for productId: "+dto.getProductId()+" has been created successfully", "201 CREATED");
            }
            throw new DataIntegrityViolationException("Inventory for productId: "+dto.getProductId()+" already exists.");
        }
        throw new ProductNotFoundException("ProductId: "+ dto.getProductId()+" is not available in application");
    }

    private Inventory toEntity(InventoryDto dto) {
        return Inventory.builder()
                .name(dto.getName())
                .productId(dto.getProductId())
                .quantity(dto.getQuantity())
                .build();
    }

    private Map<String, String> createResponse(String message, String status) {
        Map<String, String> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status);
        map.put("timestamp", LocalDateTime.now().toString());
        return map;
    }
}