package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductResponseDTOTest {

    @Autowired
    private JacksonTester<ProductResponseDTO> json;

    @Test
    @DisplayName("Deve mapear corretamente de Entidade para DTO")
    void deveMapearDeDominio() {
        // Arrange
        Product entity = new Product(
                1L,
                "X-Bacon",
                new BigDecimal("35.90"),
                2L,
                "Bacon crocante",
                "xbacon.png",
                true
        );

        // Act
        ProductResponseDTO dto = ProductResponseDTO.fromDomain(entity);

        // Assert
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getName()).isEqualTo("X-Bacon");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("35.90"));
        assertThat(dto.getCategory()).isEqualTo(2L);
        assertThat(dto.getDescription()).isEqualTo("Bacon crocante");
        assertThat(dto.getImage()).isEqualTo("xbacon.png");
        assertThat(dto.isActive()).isTrue();
    }

    @Test
    @DisplayName("Deve serializar DTO para JSON corretamente")
    void deveSerializarParaJson() throws Exception {
        // Arrange
        ProductResponseDTO dto = new ProductResponseDTO(
                10L,
                "Coca-Cola",
                new BigDecimal("8.50"),
                3L,
                "Gelada",
                "coca.png",
                true
        );

        // Act
        JsonContent<ProductResponseDTO> result = json.write(dto);

        // Assert
        // Verifica se cada campo no JSON corresponde ao valor do objeto
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(10);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("Coca-Cola");
        assertThat(result).extractingJsonPathNumberValue("$.price").isEqualTo(8.50); // Jackson serializa BigDecimal como número
        assertThat(result).extractingJsonPathNumberValue("$.category").isEqualTo(3);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Gelada");
        assertThat(result).extractingJsonPathStringValue("$.image").isEqualTo("coca.png");
        assertThat(result).extractingJsonPathBooleanValue("$.active").isTrue();
    }

    @Test
    @DisplayName("Deve deserializar JSON para DTO corretamente")
    void deveDeserializarDeJson() throws Exception {
        // Arrange
        String jsonContent = """
                {
                    "id": 50,
                    "name": "Água",
                    "price": 4.00,
                    "category": 3,
                    "description": "Sem gás",
                    "image": "agua.png",
                    "active": false
                }
                """;

        // Act
        ProductResponseDTO dto = json.parseObject(jsonContent);

        // Assert
        assertThat(dto.getId()).isEqualTo(50L);
        assertThat(dto.getName()).isEqualTo("Água");
        assertThat(dto.getPrice()).isEqualTo(new BigDecimal("4.00"));
        assertThat(dto.getCategory()).isEqualTo(3L);
        assertThat(dto.getDescription()).isEqualTo("Sem gás");
        assertThat(dto.getImage()).isEqualTo("agua.png");
        assertThat(dto.isActive()).isFalse();
    }
}