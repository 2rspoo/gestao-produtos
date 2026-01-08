package com.example.order.infrastructure.adapters.in.web.Dtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
class ProductRequestDTOTest {

    @Autowired
    private JacksonTester<ProductRequestDTO> json;

    @Test
    @DisplayName("Deve serializar Record para JSON corretamente")
    void deveSerializarParaJson() throws Exception {
        // Arrange
        ProductRequestDTO dto = new ProductRequestDTO(
                1L,
                "X-Salada",
                new BigDecimal("25.50"),
                2L,
                "Pão, carne e salada",
                "xsalada.jpg",
                true
        );

        // Act
        JsonContent<ProductRequestDTO> result = json.write(dto);

        // Assert
        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.name").isEqualTo("X-Salada");
        // BigDecimal deve ser comparado como número no JSON
        assertThat(result).extractingJsonPathNumberValue("$.price").isEqualTo(25.50);
        assertThat(result).extractingJsonPathNumberValue("$.category").isEqualTo(2);
        assertThat(result).extractingJsonPathStringValue("$.description").isEqualTo("Pão, carne e salada");
        assertThat(result).extractingJsonPathStringValue("$.image").isEqualTo("xsalada.jpg");
        assertThat(result).extractingJsonPathBooleanValue("$.active").isTrue();
    }

    @Test
    @DisplayName("Deve deserializar JSON para Record corretamente")
    void deveDeserializarDeJson() throws Exception {
        // Arrange
        String jsonContent = """
                {
                    "id": 10,
                    "name": "Coca-Cola",
                    "price": 8.00,
                    "category": 3,
                    "description": "Lata 350ml",
                    "image": "coca.png",
                    "active": false
                }
                """;

        // Act
        ProductRequestDTO dto = json.parseObject(jsonContent);

        // Assert
        assertThat(dto.id()).isEqualTo(10L);
        assertThat(dto.name()).isEqualTo("Coca-Cola");
        // Comparando BigDecimal (usando String no construtor para precisão)
        assertThat(dto.price()).isEqualTo(new BigDecimal("8.00"));
        assertThat(dto.category()).isEqualTo(3L);
        assertThat(dto.description()).isEqualTo("Lata 350ml");
        assertThat(dto.image()).isEqualTo("coca.png");
        assertThat(dto.active()).isFalse();
    }
}