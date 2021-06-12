package tech.itpark.project_delivery_web.dto;

import tech.itpark.project_delivery_web.model.Product;
import tech.itpark.project_delivery_web.model.user.User;
import tech.itpark.project_delivery_web.model.enums.CartStatus;

import java.time.LocalDateTime;
import java.util.List;

public class CartDto {

    private Long id;

    private User owner;

    private List<Product> products;

    private LocalDateTime creationDate;

    private Long totalPrice;

    private CartStatus cartStatus;
}
