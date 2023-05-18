package com.piyush.productservice.controller;

import com.piyush.productservice.dto.ProductDto;
import com.piyush.productservice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRestController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> createProduct(@Valid @RequestBody ProductDto productDto) {
        Map<String, String> response = service.createProduct(productDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String name){
        ProductDto response = service.getProductByName(name);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        Map<String, String> response = service.updateProduct(id, dto);
        return ResponseEntity.ok(response);
    }
}
