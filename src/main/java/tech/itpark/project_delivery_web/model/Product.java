package tech.itpark.project_delivery_web.model;

import tech.itpark.project_delivery_web.model.enums.Category;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    private String name;

    private String imageUri;

    private Long price;

    @Enumerated(EnumType.STRING)
    private Category category;
}
