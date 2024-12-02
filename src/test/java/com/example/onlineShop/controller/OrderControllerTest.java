package com.example.onlineShop.controller;

import com.example.onlineShop.dto.OrderRequestDto;
import com.example.onlineShop.model.Customer;
import com.example.onlineShop.model.ShopOrder;
import com.example.onlineShop.service.OrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

public class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

//    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void createOrder_ShouldReturnCreatedOrder() throws Exception {
        // Given
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        orderRequestDto.setCustomerId(1L);
        orderRequestDto.setProductIds(Arrays.asList(1L, 2L));
        orderRequestDto.setShippingAddress("123 Main St");

        ShopOrder order = new ShopOrder();
        order.setOrderId(1L);
        order.setCustomer(new Customer(1L, "John", "Doe", "john.doe@example.com", "123456789"));
        order.setTotalPrice(2000.00);
        order.setOrderStatus("PENDING");
        order.setShippingAddress("123 Main St");

        when(orderService.createOrder(any(OrderRequestDto.class))).thenReturn(order);

        // When & Then
        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequestDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.customer.firstName").value("John"))
                .andExpect(jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(jsonPath("$.totalPrice").value(2000.00))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));
    }

    @Test
    void getOrderById_ShouldReturnOrderDetails() throws Exception {
        // Given
        ShopOrder order = new ShopOrder();
        order.setOrderId(1L);
        order.setCustomer(new Customer(1L, "John", "Doe", "john.doe@example.com", "123456789"));
        order.setTotalPrice(2000.00);
        order.setOrderStatus("PENDING");
        order.setShippingAddress("123 Main St");

        when(orderService.getOrderById(1L)).thenReturn(order);

        // When & Then
        mockMvc.perform(get("/api/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.customer.firstName").value("John"))
                .andExpect(jsonPath("$.shippingAddress").value("123 Main St"))
                .andExpect(jsonPath("$.totalPrice").value(2000.00))
                .andExpect(jsonPath("$.orderStatus").value("PENDING"));
    }
}
