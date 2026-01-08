package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;

import com.example.order.domain.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryJpaEntityTest {

    @Test
    @DisplayName("Deve converter corretamente de JpaEntity para Domain (toDomain)")
    void deveConverterParaDominio() {
        // Arrange
        CategoryJpaEntity jpaEntity = new CategoryJpaEntity(1L, "Lanches");

        // Act
        Category domainEntity = jpaEntity.toDomain();

        // Assert
        assertThat(domainEntity).isNotNull();
        assertThat(domainEntity.getId()).isEqualTo(1L);
        assertThat(domainEntity.getName()).isEqualTo("Lanches");
    }

    @Test
    @DisplayName("Deve converter corretamente de Domain para JpaEntity (fromDomain)")
    void deveConverterDeDominio() {
        // Arrange
        Category domainEntity = new Category(5L, "Bebidas");

        // Act
        CategoryJpaEntity jpaEntity = CategoryJpaEntity.fromDomain(domainEntity);

        // Assert
        assertThat(jpaEntity).isNotNull();
        assertThat(jpaEntity.getId()).isEqualTo(5L);
        assertThat(jpaEntity.getName()).isEqualTo("Bebidas");
    }

    @Test
    @DisplayName("Deve funcionar construtor vazio e setters (requisito do JPA)")
    void deveTestarConstrutorPadraoESetters() {
        // Arrange
        CategoryJpaEntity entity = new CategoryJpaEntity();

        // Act
        entity.setId(10L);
        entity.setName("Sobremesa");

        // Assert
        assertThat(entity.getId()).isEqualTo(10L);
        assertThat(entity.getName()).isEqualTo("Sobremesa");
    }
}