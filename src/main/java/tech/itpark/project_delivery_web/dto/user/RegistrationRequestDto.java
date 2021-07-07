package tech.itpark.project_delivery_web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationRequestDto {
  private String email;
  private String password;
  private String secret;
}
