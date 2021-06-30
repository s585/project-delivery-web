package tech.itpark.project_delivery_web.model.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.Order;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "deliverers")
@Getter
@Setter
public class Deliverer extends AbstractUser {

    @OneToMany
    private List<Order> orders;
}
