// src/main/java/com/example/order/application/ports/in/GetAllActiveProductsUseCase.java
package com.example.order.application.ports.in.Product;
import com.example.order.domain.entities.Product;
import java.util.List;
public interface GetAllActiveProductsUseCase { List<Product> execute(); }