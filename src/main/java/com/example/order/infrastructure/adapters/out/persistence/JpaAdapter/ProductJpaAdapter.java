// src/main/java/com/example/order/infrastructure/adapters/out/persistence/ProductJpaAdapter.java
package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;
import com.example.order.application.ports.out.ProductRepositoryPort;
import com.example.order.domain.entities.Product;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.ProductJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaProductRepository;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductJpaAdapter implements ProductRepositoryPort {
    private final SpringDataJpaProductRepository jpaRepository;

    public ProductJpaAdapter(SpringDataJpaProductRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll().stream().map(ProductJpaEntity::toDomain).collect(Collectors.toList());
    }
    @Override
    public List<Product> findAllActive() {
        return jpaRepository.findByActive(true).stream().map(ProductJpaEntity::toDomain).collect(Collectors.toList());
    }
    @Override
    public List<Product> findByCategory(Long category) {
        return jpaRepository.findByCategory(category).stream().map(ProductJpaEntity::toDomain).collect(Collectors.toList());
    }
    @Override
    public Optional<Product> findById(Long id) {
        return jpaRepository.findById(id).map(ProductJpaEntity::toDomain);
    }
    @Override
    public Product save(Product product) {
        ProductJpaEntity jpaEntity = ProductJpaEntity.fromDomain(product);
        ProductJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return savedEntity.toDomain();
    }
}