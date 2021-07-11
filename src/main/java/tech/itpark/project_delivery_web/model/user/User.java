package tech.itpark.project_delivery_web.model.user;

import lombok.*;
import tech.itpark.project_delivery_web.model.Order;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
public class User extends AuthUser {

    @OneToMany
    private List<Order> carts;

    @OneToMany
    private List<Order> orders;

}
