package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest // 1. Configura o ambiente apenas para testes de JSON
class CategoryResponseDTOTest {

    @Autowired
    private JacksonTester<CategoryResponseDTO> json; // 2. Utilitário para testar JSON

    @Test
    @DisplayName("Deve mapear corretamente de Entidade para DTO")
    void deveMapearDeDominio() {
        // Arrange
        Category entity = new Category(10L, "Sobremesa");

        // Act
        CategoryResponseDTO dto = CategoryResponseDTO.fromDomain(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(10L);
        assertThat(dto.getName()).isEqualTo("Sobremesa");
    }

    @Test
    @DisplayName("Deve serializar DTO para JSON corretamente")
    void deveSerializarParaJson() throws Exception {
        // Arrange
        CategoryResponseDTO dto = new CategoryResponseDTO(1L, "Lanche");

        // Act
        JsonContent<CategoryResponseDTO> result = json.write(dto);

        // Assert
        // Verifica se o JSON gerado tem os campos esperados
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Lanche");
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTO corretamente")
    void deveDeserializarDeJson() throws Exception {
        // Arrange
        String jsonContent = """
                {
                    "id": 5,
                    "name": "Bebida"
                }
                """;

        // Act
        CategoryResponseDTO dto = json.parseObject(jsonContent);

        // Assert
        assertThat(dto.getId()).isEqualTo(5L);
        assertThat(dto.getName()).isEqualTo("Bebida");
    }

    @Test
    @DisplayName("Deve funcionar construtor padrão e setters")
    void deveTestarGettersSetters() {
        // Arrange
        CategoryResponseDTO dto = new CategoryResponseDTO();

        // Act
        dto.setId(99L);
        dto.setName("Teste");

        // Assert
        assertThat(dto.getId()).isEqualTo(99L);
        assertThat(dto.getName()).isEqualTo("Teste");
    }
}