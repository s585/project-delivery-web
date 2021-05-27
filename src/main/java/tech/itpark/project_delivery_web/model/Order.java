package tech.itpark.project_delivery_web.model;

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
    private User owner;

    @ManyToMany
    private List<Product> products;

    @ManyToOne
    private Deliverer deliverer;

    private LocalDateTime creationDate;

    private Long deliveryPrice;

    private Long totalPrice;

    private OrderStatus orderStatus;
}
