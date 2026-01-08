package com.example.order.infrastructure.adapters.out.persistence.JpaAdapter;

import com.example.order.domain.entities.Category;
import com.example.order.infrastructure.adapters.out.persistence.JpaEntity.CategoryJpaEntity;
import com.example.order.infrastructure.adapters.out.persistence.SpringDataJpa.SpringDataJpaCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryJpaAdapterTest {

    @Mock // Mockamos o repositório do Spring (infraestrutura)
    private SpringDataJpaCategoryRepository springDataRepository;

    @InjectMocks // Injetamos o mock dentro do nosso Adapter
    private CategoryJpaAdapter categoryJpaAdapter;

    @Test
    @DisplayName("Deve buscar todas as categorias e mapear para o domínio corretamente")
    void deveBuscarTodasCategoriasEMapear() {
        // Arrange
        // 1. Criamos mocks das entidades JPA (Banco)
        CategoryJpaEntity entity1 = mock(CategoryJpaEntity.class);
        CategoryJpaEntity entity2 = mock(CategoryJpaEntity.class);

        // 2. Criamos as entidades de domínio esperadas
        Category domain1 = new Category(1L, "Lanche");
        Category domain2 = new Category(2L, "Bebida");

        // 3. Ensinamos o mock da entidade JPA a retornar o domínio quando toDomain() for chamado
        when(entity1.toDomain()).thenReturn(domain1);
        when(entity2.toDomain()).thenReturn(domain2);

        // 4. Ensinamos o repositório a retornar a lista de entidades JPA
        when(springDataRepository.findAll()).thenReturn(Arrays.asList(entity1, entity2));

        // Act (Executar o método do Adapter)
        List<Category> resultado = categoryJpaAdapter.findAll();

        // Assert (Verificações)
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getId()).isEqualTo(1L);
        assertThat(resultado.get(0).getName()).isEqualTo("Lanche");
        assertThat(resultado.get(1).getName()).isEqualTo("Bebida");

        // Verifica se o repositório foi chamado
        verify(springDataRepository, times(1)).findAll();
        // Verifica se a conversão foi chamada para cada item
        verify(entity1, times(1)).toDomain();
        verify(entity2, times(1)).toDomain();
    }

    @Test
    @DisplayName("Deve retornar lista vazia se o repositório não encontrar nada")
    void deveRetornarVazioSeRepositorioVazio() {
        // Arrange
        when(springDataRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Category> resultado = categoryJpaAdapter.findAll();

        // Assert
        assertThat(resultado).isEmpty();
        verify(springDataRepository, times(1)).findAll();
    }
}