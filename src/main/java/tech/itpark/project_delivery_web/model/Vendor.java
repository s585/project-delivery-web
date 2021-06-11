package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "vendors")
public class Vendor extends LoginableUser {

    @OneToMany
    private List<Product> products;
}
