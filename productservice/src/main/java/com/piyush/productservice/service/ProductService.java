package com.piyush.productservice.service;

import com.piyush.productservice.dto.ProductDto;
import com.piyush.productservice.exception.ProductNotFoundException;
import com.piyush.productservice.model.Product;
import com.piyush.productservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public Map<String, String> createProduct(ProductDto productDto) {
        boolean productAvailable = repository.existsByName(productDto.getName());
        if (!productAvailable) {
            Product product = toEntity(productDto);
            repository.save(product);
            log.info("product is successfully added in the application. ProductId: " + product.getId());
            Map<String, String> map = new HashMap<>();
            map.put("message", "Product " + product.getName() + "has been added to application. ProductId: " + product.getId());
            map.put("status", "201 CREATED");
            map.put("timestamp", LocalDateTime.now().toString());
            return map;
        }
        throw new DataIntegrityViolationException("Product " + productDto.getName() + " already exists in the application");
    }

    public ProductDto getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product having ID: " + id + " does not exists in application"));
        return toDto(product);
    }

    public Map<String, String> updateProduct(Long id, ProductDto dto) {
        boolean productAvailable = repository.existsById(id);
        if (productAvailable) {
            Product product = repository.findById(id).get();
            product.setName(dto.getName());
            product.setDescription(dto.getDescription());
            product.setPrice(dto.getPrice());
            repository.save(product);

            Map<String, String> map = new HashMap<>();
            map.put("message", "your product: " + dto.getName() + " has been updated successfully");
            map.put("status", "200 OK");
            map.put("timestamp", LocalDateTime.now().toString());
            return map;
        }
        throw new ProductNotFoundException("Product with the Id: " + id + " is not available in the application");
    }

    public Map<String, String> deleteProduct(String name) {
        boolean productAvailable = repository.existsByName(name);
        if (productAvailable) {
            repository.deleteByName(name);
            Map<String, String> map = new HashMap<>();
            map.put("message", "Product: " + name + " has been removed from the application");
            map.put("status", "200 OK");
            map.put("timestamp", LocalDateTime.now().toString());
            return map;
        }
        throw new ProductNotFoundException("Product: " + name + " is not available in the application");
    }

    private Product toEntity(ProductDto dto) {
        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }

    private ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    public Boolean checkForProductById(Long productId) {
        return repository.existsById(productId);
    }
}
