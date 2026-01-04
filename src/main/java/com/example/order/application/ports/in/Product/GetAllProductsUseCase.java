// src/main/java/com/example/order/application/ports/in/GetAllProductsUseCase.java
package com.example.order.application.ports.in.Product;
import com.example.order.domain.entities.Product;
import java.util.List;
public interface GetAllProductsUseCase { List<Product> execute(); }