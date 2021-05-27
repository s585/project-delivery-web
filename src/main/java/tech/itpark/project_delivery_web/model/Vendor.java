package tech.itpark.project_delivery_web.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "vendors")
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String secret;

    private String name;

    private String address;

    private Double lon;

    private Double lat;

    @OneToMany
    private List<Product> products;
}
