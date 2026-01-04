// src/main/java/com/example/order/application/usecases/DeactivateProductService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.DeactivateProductUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import com.example.order.shared.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class DeactivateProductService implements DeactivateProductUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public DeactivateProductService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    @Transactional
    public void execute(Long productId) {
        Product product = productRepositoryPort.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));
        product.setActive(false);
        productRepositoryPort.save(product);
    }
}