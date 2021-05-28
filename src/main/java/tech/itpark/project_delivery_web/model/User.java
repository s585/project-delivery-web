package tech.itpark.project_delivery_web.model;

import lombok.*;
import tech.itpark.project_delivery_web.model.enums.UserStatus;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String secret;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    private String name;

    private String email;

    private String address;

    @ManyToOne
    private Role role;

    @OneToMany
    private List<Order> orders;

    private Double longitude;

    private Double latitude;
}
