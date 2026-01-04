// src/main/java/com/example/order/application/ports/in/GetAllCategoriesUseCase.java
package com.example.order.application.ports.in.Category;

import com.example.order.domain.entities.Category;
import java.util.List;

public interface GetAllCategoriesUseCase {
    List<Category> execute();
}