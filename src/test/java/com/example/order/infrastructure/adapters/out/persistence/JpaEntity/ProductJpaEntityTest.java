package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;

import com.example.order.domain.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductJpaEntityTest {

    @Test
    @DisplayName("Deve converter corretamente de JpaEntity para Domain (toDomain)")
    void deveConverterParaDominio() {
        // Arrange
        ProductJpaEntity jpaEntity = new ProductJpaEntity(
                1L,
                "X-Burguer",
                new BigDecimal("20.00"),
                1L,
                "Queijo e carne",
                "foto.png",
                true
        );

        // Act
        Product domain = jpaEntity.toDomain();

        // Assert
        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getName()).isEqualTo("X-Burguer");
        assertThat(domain.getPrice()).isEqualTo(new BigDecimal("20.00"));
        assertThat(domain.getCategory()).isEqualTo(1L);
        assertThat(domain.getDescription()).isEqualTo("Queijo e carne");
        assertThat(domain.getImage()).isEqualTo("foto.png");
        assertThat(domain.isActive()).isTrue();
    }

    @Test
    @DisplayName("Deve converter corretamente de Domain para JpaEntity (fromDomain)")
    void deveConverterDeDominio() {
        // Arrange
        Product domain = new Product(
                2L,
                "Coca-Cola",
                new BigDecimal("8.50"),
                2L,
                "Lata",
                "coca.png",
                false
        );

        // Act
        ProductJpaEntity jpaEntity = ProductJpaEntity.fromDomain(domain);

        // Assert
        assertThat(jpaEntity).isNotNull();
        assertThat(jpaEntity.getId()).isEqualTo(2L);
        assertThat(jpaEntity.getName()).isEqualTo("Coca-Cola");
        assertThat(jpaEntity.getPrice()).isEqualTo(new BigDecimal("8.50"));
        assertThat(jpaEntity.getCategory()).isEqualTo(2L);
        assertThat(jpaEntity.getDescription()).isEqualTo("Lata");
        assertThat(jpaEntity.getImage()).isEqualTo("coca.png");
        assertThat(jpaEntity.isActive()).isFalse();
    }

    @Test
    @DisplayName("Deve funcionar construtor padrão e setters (essencial para o Hibernate)")
    void deveTestarConstrutorPadraoESetters() {
        // Arrange
        ProductJpaEntity entity = new ProductJpaEntity();

        // Act
        entity.setId(100L);
        entity.setName("Água");
        entity.setPrice(BigDecimal.ONE);
        entity.setCategory(3L);
        entity.setDescription("Sem gás");
        entity.setImage("agua.jpg");
        entity.setActive(true);

        // Assert
        assertThat(entity.getId()).isEqualTo(100L);
        assertThat(entity.getName()).isEqualTo("Água");
        assertThat(entity.getPrice()).isEqualTo(BigDecimal.ONE);
        assertThat(entity.getCategory()).isEqualTo(3L);
        assertThat(entity.getDescription()).isEqualTo("Sem gás");
        assertThat(entity.getImage()).isEqualTo("agua.jpg");
        assertThat(entity.isActive()).isTrue();
    }
}