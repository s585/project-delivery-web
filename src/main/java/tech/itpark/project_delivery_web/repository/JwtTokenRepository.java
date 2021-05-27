package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tech.itpark.project_delivery_web.model.JwtToken;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    void deleteByToken(String token);

    Optional<JwtToken> findByToken(String token);

    void deleteJwtTokenByCreationDateBefore(LocalDateTime dateTime);
}
