package com.piyush.productservice.controller;

import com.piyush.productservice.exception.ProductNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ProductExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> dataIntegrityViolationExceptionHandler(DataIntegrityViolationException ex) {
        Map<String , String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", "400 Bad Request");
        response.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, String>> productNotFoundExceptionHandler(ProductNotFoundException ex) {
        Map<String , String> response = new HashMap<>();
        response.put("message", ex.getMessage());
        response.put("status", "404 Not found");
        response.put("timestamp", LocalDateTime.now().toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
