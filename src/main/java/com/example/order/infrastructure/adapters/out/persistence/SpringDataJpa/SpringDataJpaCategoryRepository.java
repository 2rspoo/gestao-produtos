// src/main/java/com/example/order/infrastructure/adapters/out/persistence/SpringDataJpaCategoryRepository.java
package com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa;

import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// A interface recebe a entidade JPA e o tipo da chave primária (Long)
@Repository
public interface SpringDataJpaCategoryRepository extends JpaRepository<CategoryJpaEntity, Long> {
}