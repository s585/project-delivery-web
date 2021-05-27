package tech.itpark.project_delivery_web.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageUri;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;
}
