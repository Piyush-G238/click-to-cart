package com.piyush.inventoryservice.controller;

import com.piyush.inventoryservice.dto.InventoryDto;
import com.piyush.inventoryservice.services.InventoryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryRestController {

    @Autowired
    private InventoryService service;

    @PostMapping
    @CircuitBreaker(name = "product", fallbackMethod = "serviceUnavailableHandler")
    @Retry(name = "product", fallbackMethod = "serviceUnavailableHandler")
    public ResponseEntity<Map<String, String>> createInventory(@RequestBody InventoryDto dto) {
        Map<String, String> response = service.createInventory(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> listAllInventory(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<InventoryDto> inventoryDtos = service.listAllInventories(pageNo, pageSize);
        return new ResponseEntity<>(inventoryDtos, HttpStatus.FOUND);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryDto> getInventoryByProductId(@PathVariable Long productId) {
        InventoryDto dto = service.getInventoryByProductId(productId);
        return new ResponseEntity<>(dto, HttpStatusCode.valueOf(200));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteInventory(@PathVariable Long id) {
        Map<String, String> response = service.deleteInventory(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{productId}")
    public void updateInventoryByProductId(@PathVariable Long productId,@RequestBody Map<String, Long> req){
        service.updateInventoryByProductId(productId, req.get("qty"));
    }

    public ResponseEntity<Map<String, String>> serviceUnavailableHandler(RuntimeException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("message","There is some technical issue with the server. Please try again later.");
        response.put("status", "503 SERVICE_UNAVAILABLE");
        response.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
