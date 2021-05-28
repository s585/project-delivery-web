package tech.itpark.project_delivery_web.model;

import tech.itpark.project_delivery_web.model.enums.CartStatus;
import tech.itpark.project_delivery_web.model.enums.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToMany
    @JoinTable(name = "carts_products",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id"))
    private List<Product> products;

    private LocalDateTime creationDate;

    private Long totalPrice;

    @Enumerated(EnumType.STRING)
    private CartStatus cartStatus;
}
