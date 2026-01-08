package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Product.*;
import com.example.order.domain.entities.Product;
import com.example.order.infrastructure.adapters.in.web.Dtos.ProductRequestDTO;
import com.example.order.shared.exceptions.ProductNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Útil para converter objetos Java em JSON String

    // --- Mocks de todos os UseCases injetados no Controller ---
    @MockitoBean private GetAllProductsUseCase getAllProductsUseCase;
    @MockitoBean private GetAllActiveProductsUseCase getAllActiveProductsUseCase;
    @MockitoBean private GetAllProductsByCategoryUseCase getAllProductsByCategoryUseCase;
    @MockitoBean private CreateProductUseCase createProductUseCase;
    @MockitoBean private UpdateProductUseCase updateProductUseCase;
    @MockitoBean private ActivateProductUseCase activateProductUseCase;
    @MockitoBean private DeactivateProductUseCase deactivateProductUseCase;

    // --- Helper para criar produto padrão ---
    private Product criarProdutoMock(Long id, String nome) {
        return new Product(id, nome, new BigDecimal("25.50"), 1L, "Descricao", "img.png", true);
    }

    @Test
    @DisplayName("GET /product - Deve retornar lista de todos os produtos")
    void deveListarTodosProdutos() throws Exception {
        List<Product> products = Arrays.asList(
                criarProdutoMock(1L, "Lanche"),
                criarProdutoMock(2L, "Bebida")
        );

        when(getAllProductsUseCase.execute()).thenReturn(products);

        mockMvc.perform(get("/product"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Lanche"))
                .andExpect(jsonPath("$[1].name").value("Bebida"));
    }

    @Test
    @DisplayName("GET /product/active - Deve retornar apenas produtos ativos")
    void deveListarProdutosAtivos() throws Exception {
        List<Product> products = Collections.singletonList(criarProdutoMock(1L, "Ativo"));

        when(getAllActiveProductsUseCase.execute()).thenReturn(products);

        mockMvc.perform(get("/product/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Ativo"));
    }

    @Test
    @DisplayName("GET /product/category/{id} - Deve retornar produtos por categoria")
    void deveListarPorCategoria() throws Exception {
        Long categoryId = 5L;
        List<Product> products = Collections.singletonList(criarProdutoMock(1L, "Pizza"));

        when(getAllProductsByCategoryUseCase.execute(categoryId)).thenReturn(products);

        mockMvc.perform(get("/product/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Pizza"));
    }

    @Test
    @DisplayName("POST /product - Deve criar um novo produto com sucesso (Status 201)")
    void deveCriarProduto() throws Exception {
        // Arrange
        // DTO de entrada (Record ou Class)
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                null, "Novo Burger", new BigDecimal("30.00"), 1L, "Delicioso", "foto.jpg", true
        );

        // Produto que o UseCase retorna (com ID gerado)
        Product createdProduct = new Product(10L, "Novo Burger", new BigDecimal("30.00"), 1L, "Delicioso", "foto.jpg", true);

        when(createProductUseCase.execute(any(Product.class))).thenReturn(createdProduct);

        // Act & Assert
        mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated()) // 201
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("Novo Burger"));
    }

    @Test
    @DisplayName("PUT /product - Deve atualizar produto existente")
    void deveAtualizarProduto() throws Exception {
        // Arrange
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                1L, "Burger Editado", new BigDecimal("35.00"), 1L, "Editado", "foto.jpg", true
        );

        Product updatedProduct = new Product(1L, "Burger Editado", new BigDecimal("35.00"), 1L, "Editado", "foto.jpg", true);

        when(updateProductUseCase.execute(any(Product.class))).thenReturn(updatedProduct);

        // Act & Assert
        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Burger Editado"));
    }

    @Test
    @DisplayName("PUT /product - Deve retornar 400 Bad Request se ID for nulo no update")
    void deveFalharUpdateSemId() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                null, "Sem ID", BigDecimal.TEN, 1L, "Desc", "img", true
        );

        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest()); // 400
    }

    @Test
    @DisplayName("PUT /product - Deve retornar 404 se produto não for encontrado para update")
    void deveRetornar404NoUpdate() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                99L, "Inexistente", BigDecimal.TEN, 1L, "Desc", "img", true
        );

        when(updateProductUseCase.execute(any(Product.class)))
                .thenThrow(new ProductNotFoundException("Produto não encontrado"));

        mockMvc.perform(put("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isNotFound()); // 404
    }

    @Test
    @DisplayName("PUT /product/{id} - Deve ativar produto com sucesso")
    void deveAtivarProduto() throws Exception {
        Long id = 1L;
        doNothing().when(activateProductUseCase).execute(id);

        mockMvc.perform(put("/product/{id}", id))
                .andExpect(status().isOk());

        verify(activateProductUseCase, times(1)).execute(id);
    }

    @Test
    @DisplayName("DELETE /product/{id} - Deve desativar (deletar lógico) produto com sucesso")
    void deveDesativarProduto() throws Exception {
        Long id = 1L;
        doNothing().when(deactivateProductUseCase).execute(id);

        mockMvc.perform(delete("/product/{id}", id))
                .andExpect(status().isOk());

        verify(deactivateProductUseCase, times(1)).execute(id);
    }
}