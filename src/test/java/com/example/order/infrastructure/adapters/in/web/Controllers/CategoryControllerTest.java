package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Category.GetAllCategoriesUseCase;
import com.example.order.domain.entities.Category;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class) // 1. Foca o teste apenas neste Controller
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc; // 2. Ferramenta para simular requisições HTTP

    @MockitoBean // 3. Cria um Mock do UseCase que o Controller usa
    private GetAllCategoriesUseCase getAllCategoriesUseCase;

    @Test
    @DisplayName("Health Check deve retornar status 200 e mensagem correta")
    void deveRetornarHealthCheck() throws Exception {
        mockMvc.perform(get("/category/health"))
                .andExpect(status().isOk())
                .andExpect(content().string(" Aplicação OK ! "));
    }

    @Test
    @DisplayName("Deve retornar lista de categorias (DTOs) quando existirem dados")
    void deveRetornarListaDeCategorias() throws Exception {
        // Arrange (Preparar o Mock)
        Category cat1 = new Category(1L, "Lanche");
        Category cat2 = new Category(2L, "Bebida");
        List<Category> categoriasMock = Arrays.asList(cat1, cat2);

        when(getAllCategoriesUseCase.execute()).thenReturn(categoriasMock);

        // Act & Assert (Executar e Validar)
        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) // Espera Status 200
                .andExpect(jsonPath("$.length()").value(2)) // Espera 2 itens no array
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Lanche"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Bebida"));
    }

    @Test
    @DisplayName("Deve retornar lista vazia (Status 200) quando não houver categorias")
    void deveRetornarListaVazia() throws Exception {
        // Arrange
        when(getAllCategoriesUseCase.execute()).thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Array vazio
    }
}