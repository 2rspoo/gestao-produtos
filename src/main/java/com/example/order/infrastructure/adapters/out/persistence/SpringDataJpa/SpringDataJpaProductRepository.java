// src/main/java/com/example/order/infrastructure/adapters/out/persistence/SpringDataJpaProductRepository.java
package com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.ProductJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SpringDataJpaProductRepository extends JpaRepository<ProductJpaEntity, Long> {
    List<ProductJpaEntity> findByActive(boolean active);
    List<ProductJpaEntity> findByCategory(Long category);
}