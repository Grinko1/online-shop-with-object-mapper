package com.example.onlineShop.controller;

import com.example.onlineShop.model.Product;
import com.example.onlineShop.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllProducts_ShouldReturnListOfProducts() throws Exception {
        // Given
        Product product1 = new Product(1L, "Product 1", "Description 1", 100.00, 10);
        Product product2 = new Product(2L, "Product 2", "Description 2", 200.00, 20);
        List<Product> products = Arrays.asList(product1, product2);

        when(productService.getAllProducts()).thenReturn(products);

        // When & Then
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(1))
                .andExpect(jsonPath("$[1].productId").value(2))
                .andExpect(jsonPath("$[0].name").value("Product 1"))
                .andExpect(jsonPath("$[1].name").value("Product 2"));
    }

    @Test
    void getProductById_ShouldReturnProductDetails() throws Exception {
        // Given
        Product product = new Product(1L, "Product 1", "Description 1", 100.00, 10);

        when(productService.getProductById(1L)).thenReturn(product);

        // When & Then
        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("Product 1"))
                .andExpect(jsonPath("$.price").value(100.00));
    }

    @Test
    void createProduct_ShouldReturnCreatedProduct() throws Exception {
        // Given
        Product product = new Product(null, "New Product", "New product description", 50.00, 30);
        Product savedProduct = new Product(3L, "New Product", "New product description", 50.00, 30);

        when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);

        // When & Then
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(3))
                .andExpect(jsonPath("$.name").value("New Product"));
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct() throws Exception {
        // Given
        Product updatedProduct = new Product(1L, "Updated Product", "Updated description", 150.00, 20);

        when(productService.updateProduct(1L, updatedProduct)).thenReturn(updatedProduct);

        // When & Then
        mockMvc.perform(put("/api/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("Updated Product"))
                .andExpect(jsonPath("$.price").value(150.00));
    }

    @Test
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        // When & Then
        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());

        verify(productService).deleteProduct(1L);
    }
}
