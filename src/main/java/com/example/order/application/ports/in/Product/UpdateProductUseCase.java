// src/main/java/com/example/order/application/ports/in/UpdateProductUseCase.java
package com.example.order.application.ports.in.Product;
import com.example.order.domain.entities.Product;
public interface UpdateProductUseCase { Product execute(Product product); }