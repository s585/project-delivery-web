package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.enums.CartStatus;
import tech.itpark.project_delivery_web.model.user.User;
import tech.itpark.project_delivery_web.model.user.Vendor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

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
