// src/main/java/com/example/order/infrastructure/adapters/out/persistence/ProductJpaEntity.java
package com.example.order.infrastructure.adapters.out.persistence.JpaEntity;
import com.example.order.domain.entities.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product")
public class ProductJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private BigDecimal price;
    private Long category;
    private String description;
    private String image;
    private boolean active;

    public ProductJpaEntity() {}
    public ProductJpaEntity(Long id, String name, BigDecimal price, Long category, String description, String image, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.active = active;
    }

    public Product toDomain() {
        return new Product(this.id, this.name, this.price, this.category, this.description, this.image, this.active);
    }
    public static ProductJpaEntity fromDomain(Product domain) {
        return new ProductJpaEntity(domain.getId(), domain.getName(), domain.getPrice(), domain.getCategory(), domain.getDescription(), domain.getImage(), domain.isActive());
    }
    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Long getCategory() { return category; }
    public void setCategory(Long category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}