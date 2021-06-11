package tech.itpark.project_delivery_web.model;

import lombok.*;
import tech.itpark.project_delivery_web.model.enums.UserStatus;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
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
