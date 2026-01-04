// src/main/java/com/example/order/application/usecases/UpdateProductService.java
        package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.UpdateProductUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import com.example.order.shared.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class UpdateProductService implements UpdateProductUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public UpdateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    @Transactional
    public Product execute(Product product) {
        productRepositoryPort.findById(product.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + product.getId()));
        return productRepositoryPort.save(product);
    }
}