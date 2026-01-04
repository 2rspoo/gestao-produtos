// src/main/java/com/example/order/application/ports/out/CategoryRepositoryPort.java
package com.example.order.application.ports.out;

import com.example.order.domain.entities.Category;
import java.util.List;

public interface CategoryRepositoryPort {
    List<Category> findAll();
}