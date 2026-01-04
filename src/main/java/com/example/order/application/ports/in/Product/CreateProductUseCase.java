// src/main/java/com/example/order/application/ports/in/CreateProductUseCase.java
package com.example.order.application.ports.in.Product;
import com.example.order.domain.entities.Product;
public interface CreateProductUseCase { Product execute(Product product); }