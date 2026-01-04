// src/main/java/com/example/order/application/usecases/GetAllProductsService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.GetAllProductsUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllProductsService implements GetAllProductsUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public GetAllProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    public List<Product> execute() {
        return productRepositoryPort.findAll();
    }
}