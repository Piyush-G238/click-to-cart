package com.piyush.orderservice.controller;

import com.piyush.orderservice.dto.OrderDto;
import com.piyush.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderRestController {

    @Autowired
    private OrderService service;

    @PostMapping
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderDto dto) {
        Map<String, String> res = service.createOrder(dto);
        return new ResponseEntity<>(res, HttpStatusCode.valueOf(201));
    }
}
