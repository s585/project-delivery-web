package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.enums.OrderStatus;
import tech.itpark.project_delivery_web.model.user.Deliverer;
import tech.itpark.project_delivery_web.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

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
