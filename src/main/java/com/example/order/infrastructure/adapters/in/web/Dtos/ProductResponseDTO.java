// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/ProductResponseDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;

import com.example.order.domain.entities.Product;
import java.math.BigDecimal;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long category;
    private String description;
    private String image;
    private boolean active;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, BigDecimal price, Long category, String description, String image, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.active = active;
    }

    public static ProductResponseDTO fromDomain(Product product) {
        return new ProductResponseDTO(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getDescription(), product.getImage(), product.isActive());
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Long getCategory() { return category; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public boolean isActive() { return active; }
}