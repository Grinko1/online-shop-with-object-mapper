package com.example.onlineShop.controller;

import com.example.onlineShop.dto.OrderRequestDto;
import com.example.onlineShop.model.ShopOrder;
import com.example.onlineShop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ShopOrder> createOrder(@RequestBody @Valid OrderRequestDto order) {
        return new ResponseEntity<>(orderService.createOrder(order), HttpStatus.CREATED);
    }

    @GetMapping("/{orderId}")
    public ShopOrder getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }
}
