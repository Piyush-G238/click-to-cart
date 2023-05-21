package com.piyush.orderservice.controller;

import com.piyush.orderservice.dto.OrderDto;
import com.piyush.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    @Autowired
    private OrderService service;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "serviceUnavailableHandler")
    @Retry(name = "inventory", fallbackMethod = "serviceUnavailableHandler")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderDto dto) {
        Map<String, String> res = service.createOrder(dto);
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(201));
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getOrderById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteOrderById(id));
    }

    public ResponseEntity<Map<String, String>> serviceUnavailableHandler(RuntimeException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("message","There is some technical issue with the server. Please try again later.");
        response.put("status", "503 SERVICE_UNAVAILABLE");
        response.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }
}
