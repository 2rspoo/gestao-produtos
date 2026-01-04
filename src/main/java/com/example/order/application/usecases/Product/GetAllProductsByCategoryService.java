// src/main/java/com/example/order/application/usecases/GetAllProductsByCategoryService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.GetAllProductsByCategoryUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllProductsByCategoryService implements GetAllProductsByCategoryUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public GetAllProductsByCategoryService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    public List<Product> execute(Long category) {
        return productRepositoryPort.findByCategory(category);
    }
}