package com.example.order.domain.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CategoryTest {

    @Test
    @DisplayName("Deve criar uma categoria corretamente com o construtor")
    void deveCriarCategoria() {
        // Arrange & Act
        Category category = new Category(1L, "Bebidas");

        // Assert
        assertThat(category).isNotNull();
        assertThat(category.getId()).isEqualTo(1L);
        assertThat(category.getName()).isEqualTo("Bebidas");
    }

    @Test
    @DisplayName("Deve alterar valores através dos Setters")
    void deveAlterarValores() {
        // Arrange
        Category category = new Category(1L, "Antigo");

        // Act
        category.setId(2L);
        category.setName("Novo Nome");

        // Assert
        assertThat(category.getId()).isEqualTo(2L);
        assertThat(category.getName()).isEqualTo("Novo Nome");
    }

    @Test
    @DisplayName("Deve considerar iguais duas categorias com o mesmo ID (mesmo com nomes diferentes)")
    void deveSerIgualSeIdForIgual() {
        // Arrange
        // Note que os nomes são diferentes, mas o ID é o mesmo (10L)
        Category category1 = new Category(10L, "Lanche");
        Category category2 = new Category(10L, "Sobremesa");

        // Act & Assert
        // Como o seu equals usa apenas o ID, eles devem ser considerados iguais
        assertThat(category1).isEqualTo(category2);

        // Se são iguais, o HashCode TAMBÉM deve ser igual (regra do Java)
        assertThat(category1.hashCode()).isEqualTo(category2.hashCode());
    }

    @Test
    @DisplayName("Não deve considerar iguais duas categorias com IDs diferentes")
    void naoDeveSerIgualSeIdForDiferente() {
        // Arrange
        Category category1 = new Category(1L, "Lanche");
        Category category2 = new Category(2L, "Lanche"); // Mesmo nome, ID diferente

        // Act & Assert
        assertThat(category1).isNotEqualTo(category2);
        assertThat(category1.hashCode()).isNotEqualTo(category2.hashCode());
    }

    @Test
    @DisplayName("Não deve ser igual a null ou a objetos de outra classe")
    void naoDeveSerIgualANullOuOutrosTipos() {
        Category category = new Category(1L, "Teste");

        assertThat(category).isNotEqualTo(null);
        assertThat(category).isNotEqualTo(new Object());
        assertThat(category).isNotEqualTo("Uma String qualquer");
    }
}