package tech.itpark.project_delivery_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.itpark.project_delivery_web.model.JwtToken;

import java.util.List;
import java.util.Optional;

public interface JwtTokenRepository extends JpaRepository<JwtToken, Long> {

    Object save(JwtToken jwtToken);

    List<JwtToken> findAll();

    Optional<JwtToken> findByToken(String token);

    void cleanup(Long expiredTimeInMilliseconds);

    void update(JwtToken jwtToken);

    void deleteByToken(String token);
}
