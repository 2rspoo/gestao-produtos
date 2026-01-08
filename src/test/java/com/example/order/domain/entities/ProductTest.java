package com.example.order.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {

    @Test
    @DisplayName("Deve criar produto corretamente com construtor completo (com ID)")
    void deveCriarProdutoComId() {
        // Arrange
        Long id = 1L;
        String name = "X-Bacon";
        BigDecimal price = new BigDecimal("35.90");
        Long categoryId = 2L;
        String description = "Pão, carne e bacon";
        String image = "xbacon.png";
        boolean active = true;

        // Act
        Product product = new Product(id, name, price, categoryId, description, image, active);

        // Assert
        assertThat(product.getId()).isEqualTo(id);
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
        assertThat(product.getCategory()).isEqualTo(categoryId);
        assertThat(product.getDescription()).isEqualTo(description);
        assertThat(product.getImage()).isEqualTo(image);
        assertThat(product.isActive()).isTrue();
    }

    @Test
    @DisplayName("Deve criar produto corretamente com construtor sem ID")
    void deveCriarProdutoSemId() {
        // Arrange
        String name = "Coca-Cola";
        BigDecimal price = new BigDecimal("8.00");
        Long categoryId = 3L;
        String description = "Lata 350ml";
        String image = "coca.png";
        boolean active = true;

        // Act
        Product product = new Product(name, price, categoryId, description, image, active);

        // Assert
        assertThat(product.getId()).isNull(); // ID deve ser nulo
        assertThat(product.getName()).isEqualTo(name);
        assertThat(product.getPrice()).isEqualTo(price);
    }

    @Test
    @DisplayName("Deve atualizar valores corretamente usando Setters")
    void deveAtualizarValoresComSetters() {
        // Arrange
        Product product = new Product(null, null, null, null, null, false);

        // Act
        product.setId(10L);
        product.setName("Novo Nome");
        product.setPrice(BigDecimal.TEN);
        product.setCategory(5L);
        product.setDescription("Nova Descrição");
        product.setImage("nova.jpg");
        product.setActive(true);

        // Assert
        assertThat(product.getId()).isEqualTo(10L);
        assertThat(product.getName()).isEqualTo("Novo Nome");
        assertThat(product.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(product.getCategory()).isEqualTo(5L);
        assertThat(product.getDescription()).isEqualTo("Nova Descrição");
        assertThat(product.getImage()).isEqualTo("nova.jpg");
        assertThat(product.isActive()).isTrue();
    }

    @Test
    @DisplayName("Deve considerar iguais dois produtos com o mesmo ID (mesmo com outros dados diferentes)")
    void deveSerIgualSeIdForIgual() {
        // Arrange
        // Produtos diferentes em conteúdo, mas mesmo ID
        Product p1 = new Product(1L, "Burger A", BigDecimal.ONE, 1L, "Desc A", "img", true);
        Product p2 = new Product(1L, "Burger B", BigDecimal.TEN, 2L, "Desc B", "img2", false);

        // Act & Assert
        assertThat(p1).isEqualTo(p2); // Verifica o equals()
        assertThat(p1.hashCode()).isEqualTo(p2.hashCode()); // Verifica o hashCode()
    }

    @Test
    @DisplayName("Não deve considerar iguais produtos com IDs diferentes")
    void naoDeveSerIgualSeIdForDiferente() {
        // Arrange
        Product p1 = new Product(1L, "Burger", BigDecimal.TEN, 1L, "Desc", "img", true);
        Product p2 = new Product(2L, "Burger", BigDecimal.TEN, 1L, "Desc", "img", true);

        // Act & Assert
        assertThat(p1).isNotEqualTo(p2);
        assertThat(p1.hashCode()).isNotEqualTo(p2.hashCode());
    }

    @Test
    @DisplayName("Não deve ser igual a null ou outro tipo de objeto")
    void naoDeveSerIgualANullOuOutroTipo() {
        Product product = new Product(1L, "Teste", BigDecimal.ONE, 1L, "Desc", "img", true);

        assertThat(product).isNotEqualTo(null);
        assertThat(product).isNotEqualTo(new Object());
    }
}