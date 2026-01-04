// src/main/java/com/example/order/infrastructure/adapters/in/web/dtos/ProductRequestDTO.java
package com.example.order.infrastructure.adapters.in.web.Dtos;

import java.math.BigDecimal;

public record ProductRequestDTO(Long id, String name, BigDecimal price, Long category, String description, String image, Boolean active) {}