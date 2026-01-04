// src/main/java/com/example/order/infrastructure/adapters/out/persistence/CategoryJpaAdapter.java
package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;

import com.example.order.application.ports.out.CategoryRepositoryPort;
import com.example.order.domain.entities.Category;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CategoryJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryJpaAdapter implements CategoryRepositoryPort {

    private final SpringDataJpaCategoryRepository jpaRepository;

    public CategoryJpaAdapter(SpringDataJpaCategoryRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Category> findAll() {
        // Mapeia a entidade JPA para a entidade de domínio
        return jpaRepository.findAll().stream()
                .map(CategoryJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}