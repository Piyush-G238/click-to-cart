package com.piyush.inventoryservice.controller;

import com.piyush.inventoryservice.dto.InventoryDto;
import com.piyush.inventoryservice.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/inventories")
public class InventoryRestController {

    @Autowired
    private InventoryService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> createInventory(@RequestBody InventoryDto dto) {
        Map<String, String> response = service.createInventory(dto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @GetMapping
    public ResponseEntity<List<InventoryDto>> listAllInventory(@RequestParam(name = "pageNo", defaultValue = "0") int pageNo, @RequestParam(name = "pageSize", defaultValue = "5") int pageSize) {
        List<InventoryDto> inventoryDtos = service.listAllInventories(pageNo, pageSize);
        return new ResponseEntity<>(inventoryDtos, HttpStatus.FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteInventory(@PathVariable Long id) {
        Map<String, String> response = service.deleteInventory(id);
        return ResponseEntity.ok(response);
    }
}
