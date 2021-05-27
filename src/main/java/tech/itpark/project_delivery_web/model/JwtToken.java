package tech.itpark.project_delivery_web.model;

import lombok.Getter;
import lombok.Setter;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "jwt_token")
public class JwtToken{
    @Id
    private String token;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    private TokenStatus status;
}
