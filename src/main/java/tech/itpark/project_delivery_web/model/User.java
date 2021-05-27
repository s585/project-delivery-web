package tech.itpark.project_delivery_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.framework.security.Auth;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

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

    private String name;

    private String address;

    @OneToMany
    private List<Order> orders;

    private Double longitude;

    private Double latitude;
}
