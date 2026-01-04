// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/CategoryResponseDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Category;

public class CategoryResponseDTO {
    private Long id;
    private String name;

    // Construtor usado para mapear da entidade de domínio para o DTO
    public CategoryResponseDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Adicione um construtor padrão, pois é uma boa prática para frameworks de serialização
    public CategoryResponseDTO() {
    }

    // ***** MÉTODOS GETTERS ADICIONADOS *****
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Métodos setters não são estritamente necessários para este caso de uso (GET),
    // mas são boa prática para serialização
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Método de mapeamento estático
    public static CategoryResponseDTO fromDomain(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName());
    }
}