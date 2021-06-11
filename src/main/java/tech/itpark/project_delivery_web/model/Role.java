package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class Role extends BaseEntity {

    private String name;

    @Column(name = "priority")
    private Integer priority;

    @OneToMany(mappedBy = "role", orphanRemoval = true)
    private List<User> users;

    @ManyToMany
    @JoinTable(name = "role_permissions",
            joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private List<Permission> permissions;
}
