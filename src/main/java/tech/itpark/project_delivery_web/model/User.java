package tech.itpark.project_delivery_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.project_delivery_web.model.enums.UserStatus;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String secret;

    private UserStatus status;

    private String name;

    private String email;

    private String address;

    private String role;

    @OneToMany
    private List<Order> orders;

    private Double longitude;

    private Double latitude;
}
