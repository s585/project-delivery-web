package tech.itpark.project_delivery_web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TokenAuth {
  private long userId;
  private String token;
}
