// src/main/java/com/example/order/application/usecases/CreateProductService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.CreateProductUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class CreateProductService implements CreateProductUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public CreateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    @Transactional
    public Product execute(Product product) {
        return productRepositoryPort.save(product);
    }
}