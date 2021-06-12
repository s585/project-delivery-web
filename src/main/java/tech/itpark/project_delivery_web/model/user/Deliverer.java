package tech.itpark.project_delivery_web.model.user;

import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.model.user.AbstractUser;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "deliverers")
public class Deliverer extends AbstractUser {

    @OneToMany
    private List<Order> orders;
}
