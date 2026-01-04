// src/main/java/com/example/order/domain/entities/Product.java
package com.example.order.domain.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

    private Long id;
    private String name;
    private BigDecimal price;
    private Long category;
    private String description;
    private String image;
    private boolean active;

    public Product(String name, BigDecimal price, Long category, String description, String image, boolean active) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.active = active;
    }

    public Product(Long id, String name, BigDecimal price, Long category, String description, String image, boolean active) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.active = active;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}