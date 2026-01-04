// src/main/java/com/example/order/application/ports/out/ProductRepositoryPort.java
package com.example.order.application.ports.out;
import com.example.order.domain.entities.Product;
import java.util.List;
import java.util.Optional;
public interface ProductRepositoryPort {
    List<Product> findAll();
    List<Product> findAllActive();
    List<Product> findByCategory(Long category);
    Optional<Product> findById(Long id);
    Product save(Product product);
}