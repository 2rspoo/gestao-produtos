// src/main/java/com/example/order/application/usecases/CategoryService.java
package com.example.order.application.usecases.Category;

import com.example.order.application.ports.out.CategoryRepositoryPort;
import com.example.order.domain.entities.Category;
import com.example.order.application.ports.in.Category.GetAllCategoriesUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements GetAllCategoriesUseCase {

    private final CategoryRepositoryPort categoryRepositoryPort;

    public CategoryService(CategoryRepositoryPort categoryRepositoryPort) {
        this.categoryRepositoryPort = categoryRepositoryPort;
    }

    @Override
    public List<Category> execute() {
        // Lógica de  negócio específica, se houver, ficaria aqui
        return categoryRepositoryPort.findAll();
    }
}