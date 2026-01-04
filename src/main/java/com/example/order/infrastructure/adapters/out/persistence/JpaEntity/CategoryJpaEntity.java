// src/main/java/com/example/order/infrastructure/adapters/out/persistence/CategoryJpaEntity.java
package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;

import com.example.order.domain.entities.Category;
import jakarta.persistence.*;

@Entity
@Table(name = "category") // Mapeia a classe para a tabela 'categories' no banco
public class CategoryJpaEntity {

    @Id // Define 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Deixa que o banco de dados gere o ID
    private Long id;

    private String name;

    // Construtor padrão exigido pelo JPA
    public CategoryJpaEntity() {
    }

    public CategoryJpaEntity(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Métodos de mapeamento para converter entre a entidade JPA e a de domínio
    public Category toDomain() {
        return new Category(this.id, this.name);
    }

    public static CategoryJpaEntity fromDomain(Category domain) {
        return new CategoryJpaEntity(domain.getId(), domain.getName());
    }
}