package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;

import com.example.order.domain.entities.Product;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.ProductJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductJpaAdapterTest {

    @Mock
    private SpringDataJpaProductRepository springDataRepository;

    @InjectMocks
    private ProductJpaAdapter productJpaAdapter;

    // --- Helper para criar Entidade de Domínio ---
    private Product criarProdutoDominio(Long id, String nome) {
        return new Product(id, nome, BigDecimal.TEN, 1L, "Desc", "img", true);
    }

    @Test
    @DisplayName("findAll - Deve retornar lista de produtos mapeados")
    void deveBuscarTodos() {
        // Arrange
        ProductJpaEntity entityMock = mock(ProductJpaEntity.class);
        Product domainMock = criarProdutoDominio(1L, "Teste");

        when(entityMock.toDomain()).thenReturn(domainMock);
        when(springDataRepository.findAll()).thenReturn(Collections.singletonList(entityMock));

        // Act
        List<Product> result = productJpaAdapter.findAll();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Teste");
        verify(entityMock, times(1)).toDomain();
    }

    @Test
    @DisplayName("findAllActive - Deve retornar apenas produtos ativos")
    void deveBuscarAtivos() {
        // Arrange
        ProductJpaEntity entityMock = mock(ProductJpaEntity.class);
        Product domainMock = criarProdutoDominio(1L, "Ativo");

        when(entityMock.toDomain()).thenReturn(domainMock);
        when(springDataRepository.findByActive(true)).thenReturn(Collections.singletonList(entityMock));

        // Act
        List<Product> result = productJpaAdapter.findAllActive();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Ativo");
        verify(springDataRepository, times(1)).findByActive(true);
    }

    @Test
    @DisplayName("findByCategory - Deve retornar produtos da categoria especificada")
    void deveBuscarPorCategoria() {
        // Arrange
        Long categoryId = 5L;
        ProductJpaEntity entityMock = mock(ProductJpaEntity.class);
        Product domainMock = criarProdutoDominio(1L, "Pizza");

        when(entityMock.toDomain()).thenReturn(domainMock);
        when(springDataRepository.findByCategory(categoryId)).thenReturn(Collections.singletonList(entityMock));

        // Act
        List<Product> result = productJpaAdapter.findByCategory(categoryId);

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Pizza");
    }

    @Test
    @DisplayName("findById - Deve retornar Optional com produto quando encontrado")
    void deveEncontrarPorId() {
        // Arrange
        Long id = 10L;
        ProductJpaEntity entityMock = mock(ProductJpaEntity.class);
        Product domainMock = criarProdutoDominio(id, "Found");

        when(entityMock.toDomain()).thenReturn(domainMock);
        when(springDataRepository.findById(id)).thenReturn(Optional.of(entityMock));

        // Act
        Optional<Product> result = productJpaAdapter.findById(id);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("findById - Deve retornar Optional vazio quando não encontrado")
    void deveRetornarVazioSeIdNaoExiste() {
        Long id = 99L;
        when(springDataRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productJpaAdapter.findById(id);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("save - Deve converter, salvar no repositório e retornar o domínio salvo")
    void deveSalvarProduto() {
        // Arrange
        // 1. Input: Produto real (para que o método estático fromDomain funcione sem erros)
        Product produtoParaSalvar = new Product("Novo", BigDecimal.TEN, 1L, "Desc", "img", true);

        // 2. Mock do retorno do banco (JPA Entity)
        ProductJpaEntity entitySalvaMock = mock(ProductJpaEntity.class);

        // 3. Resultado esperado após converter de volta
        Product produtoSalvo = criarProdutoDominio(1L, "Novo");

        // Configuração dos Mocks
        // Quando o repositório salvar QUALQUER coisa, retorne a entidade mockada
        when(springDataRepository.save(any(ProductJpaEntity.class))).thenReturn(entitySalvaMock);
        // Quando pedirem para converter a entidade salva, retorne o produto de domínio
        when(entitySalvaMock.toDomain()).thenReturn(produtoSalvo);

        // Act
        Product resultado = productJpaAdapter.save(produtoParaSalvar);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getName()).isEqualTo("Novo");

        verify(springDataRepository, times(1)).save(any(ProductJpaEntity.class));
        verify(entitySalvaMock, times(1)).toDomain();
    }
}