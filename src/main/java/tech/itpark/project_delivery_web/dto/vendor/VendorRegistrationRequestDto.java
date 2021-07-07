package tech.itpark.project_delivery_web.dto.vendor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.itpark.project_delivery_web.model.Role;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VendorRegistrationRequestDto {

  private String email;
  private String password;
  private String secret;
  private String address;
  private Role role;
}
