package tech.itpark.project_delivery_web.model.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.BaseEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractUser extends BaseEntity {

    private String name;

    private String address;

    private Double lon;

    private Double lat;
}
