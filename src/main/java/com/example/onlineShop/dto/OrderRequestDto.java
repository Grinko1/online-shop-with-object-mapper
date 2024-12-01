package com.example.onlineShop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDto {
    @NotNull(message = "Id покупателя должно быть указано")
    private Long customerId;
    @NotEmpty(message = "Список продуктов не может быть пустым")
    @Size(min = 1, message = "Список продуктов должен содержать хотя бы один продукт")
    private List<Long> productIds;
    @NotBlank(message = "Адрес доставки должен быть указан")
    private String shippingAddress;
}
