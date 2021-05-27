package tech.itpark.project_delivery_web.service.token;

import tech.itpark.project_delivery_web.model.JwtToken;

import java.util.List;

public interface JwtTokenService {

    void create(JwtToken jwtToken);

    List<JwtToken> findAll();

    void update(JwtToken jwtToken);

    void delete(String token);

    boolean invalidate(String token);

    boolean isActive(String token);

    void cleanup(Long expiredTimeInMilliseconds);

    String parseToken(String token);
}
