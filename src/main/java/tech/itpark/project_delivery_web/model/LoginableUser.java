package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class LoginableUser extends AbstractUser {

    private String password;

    private String secret;
}
