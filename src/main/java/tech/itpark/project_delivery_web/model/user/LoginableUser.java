package tech.itpark.project_delivery_web.model.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.user.AbstractUser;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class LoginableUser extends AbstractUser {

    private String password;

    private String secret;
}
