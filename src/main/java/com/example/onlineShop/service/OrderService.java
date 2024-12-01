package com.example.onlineShop.service;

import com.example.onlineShop.dto.OrderRequestDto;
import com.example.onlineShop.model.Customer;
import com.example.onlineShop.model.Product;
import com.example.onlineShop.model.ShopOrder;
import com.example.onlineShop.repository.CustomerRepository;
import com.example.onlineShop.repository.OrderRepository;
import com.example.onlineShop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    public ShopOrder createOrder(OrderRequestDto orderRequestDto) {
        Customer customer = customerRepository.findById(orderRequestDto.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("Customer not found"));

        List<Product> products = productRepository.findAllById(orderRequestDto.getProductIds());
        if (products.isEmpty()) {
            throw new EntityNotFoundException("Products not found");
        }

        Double totalPrice = products.stream()
                .mapToDouble(Product::getPrice)
                .sum();

        ShopOrder order = new ShopOrder();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderRequestDto.getShippingAddress());
        order.setTotalPrice(totalPrice);
        order.setOrderStatus("PENDING");

        return orderRepository.save(order);
    }

    public ShopOrder getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
    }
}
