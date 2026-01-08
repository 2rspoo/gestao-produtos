package com.example.order.shared.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductNotFoundExceptionTest {

    @Test
    @DisplayName("Deve criar a exceção com a mensagem correta")
    void deveCriarExcecaoComMensagem() {
        // Arrange
        String mensagemEsperada = "Produto com ID 1 não encontrado";

        // Act
        ProductNotFoundException exception = new ProductNotFoundException(mensagemEsperada);

        // Assert
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo(mensagemEsperada);
        assertThat(exception).isInstanceOf(RuntimeException.class); // Garante que herda de RuntimeException
    }
}