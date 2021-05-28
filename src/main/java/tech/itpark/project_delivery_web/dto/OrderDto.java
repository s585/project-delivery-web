package tech.itpark.project_delivery_web.dto;

import tech.itpark.project_delivery_web.model.Deliverer;
import tech.itpark.project_delivery_web.model.Product;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.model.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderDto {

    private Long id;

    private User owner;

    private List<Product> products;

    private Deliverer deliverer;

    private LocalDateTime creationDate;

    private Long deliveryPrice;

    private Long totalPrice;

    private OrderStatus orderStatus;
}
