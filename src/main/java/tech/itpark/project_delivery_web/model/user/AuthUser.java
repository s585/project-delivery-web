package tech.itpark.project_delivery_web.model.user;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.enums.UserStatus;
import tech.itpark.project_delivery_web.model.user.AbstractUser;

import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class AuthUser extends AbstractUser {

    private String email;

    private String password;

    private String secret;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
}
