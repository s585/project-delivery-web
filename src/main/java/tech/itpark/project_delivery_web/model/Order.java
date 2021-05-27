package tech.itpark.project_delivery_web.model;

import tech.itpark.project_delivery_web.model.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;

    @ManyToOne
    @JoinColumn(name = "deliverer_id")
    private Deliverer deliverer;

    private LocalDateTime creationDate;

    private Long deliveryPrice;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
