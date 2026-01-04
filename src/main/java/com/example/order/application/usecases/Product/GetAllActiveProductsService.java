// src/main/java/com/example/order/application/usecases/GetAllActiveProductsService.java
package com.example.order.application.usecases.Product;

import com.example.order.application.ports.in.Product.GetAllActiveProductsUseCase;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllActiveProductsService implements GetAllActiveProductsUseCase {
    private final ProductRepositoryPort productRepositoryPort;
    public GetAllActiveProductsService(ProductRepositoryPort productRepositoryPort) {
        this.productRepositoryPort = productRepositoryPort;
    }
    @Override
    public List<Product> execute() {
        return productRepositoryPort.findAllActive();
    }
}
