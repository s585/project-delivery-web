package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "permissions")
public class Permission extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "permissions", fetch = FetchType.EAGER)
    private List<Role> roles;
}
