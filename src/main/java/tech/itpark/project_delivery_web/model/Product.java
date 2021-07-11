package tech.itpark.project_delivery_web.model;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.enums.Category;
import tech.itpark.project_delivery_web.model.user.Vendor;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @SerializedName("category")
    private Category category;

    private String name;

    private String imageUri;

    private Long price;

    @ManyToOne
    private Vendor vendor;


}
