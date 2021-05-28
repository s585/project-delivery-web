package tech.itpark.project_delivery_web.dto.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRecoverDto {

    String password;

    String email;

    String secret;
}
