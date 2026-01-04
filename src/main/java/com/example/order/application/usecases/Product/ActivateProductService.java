// src/main/java/com/example/order/application/usecases/ActivateProductService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.ActivateProductUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import com.example.order.shared.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class ActivateProductService implements ActivateProductUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public ActivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    @Transactional
    public void execute(Long productId) {
        Product product = productRepositoryPort.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        product.setActive(true);
        productRepositoryPort.save(product);
    }
}