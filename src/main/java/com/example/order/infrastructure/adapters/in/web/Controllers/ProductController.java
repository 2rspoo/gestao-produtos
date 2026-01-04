// src/main/java/com/example/order/infrastructure/adapters/in/web/ProductController.java
package com.example.order.infrastructure.adapters.in.web.Controllers;

import com.example.order.application.ports.in.Product.*;
import com.example.order.domain.entities.Product;
import com.example.order.infrastructure.adapters.in.web.Dtos.ProductRequestDTO;
import com.example.order.infrastructure.adapters.in.web.Dtos.ProductResponseDTO;
import com.example.order.shared.exceptions.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "null")
public class ProductController {

    private final GetAllProductsUseCase getAllProductsUseCase;
    private final GetAllActiveProductsUseCase getAllActiveProductsUseCase;
    private final GetAllProductsByCategoryUseCase getAllProductsByCategoryUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final ActivateProductUseCase activateProductUseCase;
    private final DeactivateProductUseCase deactivateProductUseCase;

    public ProductController(
            GetAllProductsUseCase getAllProductsUseCase,
            GetAllActiveProductsUseCase getAllActiveProductsUseCase,
            GetAllProductsByCategoryUseCase getAllProductsByCategoryUseCase,
            CreateProductUseCase createProductUseCase,
            UpdateProductUseCase updateProductUseCase,
            ActivateProductUseCase activateProductUseCase,
            DeactivateProductUseCase deactivateProductUseCase) {
        this.getAllProductsUseCase = getAllProductsUseCase;
        this.getAllActiveProductsUseCase = getAllActiveProductsUseCase;
        this.getAllProductsByCategoryUseCase = getAllProductsByCategoryUseCase;
        this.createProductUseCase = createProductUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.activateProductUseCase = activateProductUseCase;
        this.deactivateProductUseCase = deactivateProductUseCase;
    }

    @Operation(description = "Busca Lista de Todos Produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de produto"),
            @ApiResponse(responseCode = "400", description = "Não existe nenhum produto cadastrado")
    })
    @GetMapping
    public List<ProductResponseDTO> getAll() {
        List<Product> products = getAllProductsUseCase.execute();
        return products.stream().map(ProductResponseDTO::fromDomain).collect(Collectors.toList());
    }

    @Operation(description = "Busca Lista de Todos Produtos Ativos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de produto Ativos"),
            @ApiResponse(responseCode = "400", description = "Não existe produto ativos")
    })
    @GetMapping("/active")
    public List<ProductResponseDTO> getAllActive() {
        List<Product> products = getAllActiveProductsUseCase.execute();
        return products.stream().map(ProductResponseDTO::fromDomain).collect(Collectors.toList());
    }

    @Operation(description = "Busca Lista de produtos por categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna lista de produto por categoria"),
            @ApiResponse(responseCode = "400", description = "Não existe produto dessa categoria")
    })
    @GetMapping("/category/{id}")
    public List<ProductResponseDTO> getAllCategory(@PathVariable Long id) {
        List<Product> products = getAllProductsByCategoryUseCase.execute(id);
        return products.stream().map(ProductResponseDTO::fromDomain).collect(Collectors.toList());
    }

    @Operation(description = "Insere um novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Insere um novo produto"),
            @ApiResponse(responseCode = "400", description = "Erro ao inserir um novo produto")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO saveProduct(@RequestBody ProductRequestDTO data) {
        Product newProduct = new Product(
                data.name(),
                data.price(),
                data.category(),
                data.description(),
                data.image(),
                data.active() != null ? data.active() : true
        );
        Product createdProduct = createProductUseCase.execute(newProduct);
        return ProductResponseDTO.fromDomain(createdProduct);
    }

    @Operation(description = "Altera um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto Alterado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping
    public ResponseEntity<ProductResponseDTO> updateProduct(@RequestBody ProductRequestDTO data) {
        if (data.id() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product ID is mandatory for update.");
        }
        try {
            Product productToUpdate = new Product(data.id(), data.name(), data.price(), data.category(), data.description(), data.image(), data.active());
            Product updatedProduct = updateProductUseCase.execute(productToUpdate);
            return ResponseEntity.ok(ProductResponseDTO.fromDomain(updatedProduct));
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(description = "Ativar Produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna produto ativado"),
            @ApiResponse(responseCode = "404", description = "Não existe produto para ativar")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> activateProduct(@PathVariable Long id) {
        try {
            activateProductUseCase.execute(id);
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @Operation(description = "Deletar Produto (desativa o produto)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna produto deletado"),
            @ApiResponse(responseCode = "404", description = "Não existe produto a ser deletado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateProduct(@PathVariable Long id) {
        try {
            deactivateProductUseCase.execute(id);
            return ResponseEntity.ok().build();
        } catch (ProductNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}