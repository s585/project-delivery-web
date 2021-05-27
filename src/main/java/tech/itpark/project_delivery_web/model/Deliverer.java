package tech.itpark.project_delivery_web.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "deliverers")
public class Deliverer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Double lon;

    private Double lat;

    @OneToMany
    private List<Order> orders;
}
