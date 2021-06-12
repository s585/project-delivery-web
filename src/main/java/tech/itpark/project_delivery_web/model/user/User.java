package tech.itpark.project_delivery_web.model.user;

import lombok.*;
import tech.itpark.project_delivery_web.model.Order;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.enums.UserStatus;
import tech.itpark.project_delivery_web.model.user.LoginableUser;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
public class User extends LoginableUser {

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany
    private List<Order> orders;

}
