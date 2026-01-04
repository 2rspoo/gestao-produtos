// src/main/java/com/example/order/infrastructure/adapters/in/web/CategoryController.java
package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Category.GetAllCategoriesUseCase;
import com.example.order.domain.entities.Category;
import com.example.order.infrastructure.adapters.in.web.Dtos.CategoryResponseDTO;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "null")
public class CategoryController {

    private final GetAllCategoriesUseCase getAllCategoriesUseCase;

    public CategoryController(GetAllCategoriesUseCase getAllCategoriesUseCase) {
        this.getAllCategoriesUseCase = getAllCategoriesUseCase;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return " Aplicação OK ! ";
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        // 1. O controlador chama o caso de uso (Input Port)
        List<Category> categories = getAllCategoriesUseCase.execute();

        // 2. Mapeia a entidade de domínio para o DTO da API
        return categories.stream()
                .map(CategoryResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}