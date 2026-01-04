// src/main/java/com/example/order/shared/exceptions/ProductNotFoundException.java
package com.example.order.shared.exceptions;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}