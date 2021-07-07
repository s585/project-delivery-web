package tech.itpark.project_delivery_web.model.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.model.Product;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vendors")
public class Vendor extends AuthUser {

    @OneToMany
    private List<Order> orders;

    @OneToMany
    private List<Product> products;
}
