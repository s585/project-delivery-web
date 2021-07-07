package tech.itpark.project_delivery_web.dto.vendor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VendorAuthDto {

    private String email;
    private String password;
}
