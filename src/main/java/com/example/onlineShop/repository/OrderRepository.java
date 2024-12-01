package com.example.onlineShop.repository;

import com.example.onlineShop.model.ShopOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<ShopOrder, Long> {
}
