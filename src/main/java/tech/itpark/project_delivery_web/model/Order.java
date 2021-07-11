package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.enums.OrderStatus;
import tech.itpark.project_delivery_web.model.user.Deliverer;
import tech.itpark.project_delivery_web.model.user.User;
import tech.itpark.project_delivery_web.model.user.Vendor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "deliverer_id")
    private Deliverer deliverer;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToMany
    @JoinTable(name = "orders_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;

    private LocalDateTime creationDate;

    private Long deliveryPrice;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
