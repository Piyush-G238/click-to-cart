package com.piyush.inventoryservice.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String s) {
        super(s);
    }
}
