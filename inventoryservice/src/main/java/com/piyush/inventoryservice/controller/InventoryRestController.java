package com.piyush.inventoryservice.controller;

import com.piyush.inventoryservice.dto.InventoryDto;
import com.piyush.inventoryservice.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
}
