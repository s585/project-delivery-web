package tech.itpark.project_delivery_web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDtoAuth {

    private String email;

    private String password;
}
