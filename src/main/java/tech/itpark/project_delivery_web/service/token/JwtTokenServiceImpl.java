package tech.itpark.project_delivery_web.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.security.exeption.JwtTokenNotFoundException;
import tech.itpark.project_delivery_web.model.JwtToken;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;
import tech.itpark.project_delivery_web.repository.JwtTokenRepository;

import java.util.List;

@Service
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String NOT_FOUND_MESSAGE = "Token not found";

    private final JwtTokenRepository jwtTokenRepository;

    @Autowired
    public JwtTokenServiceImpl(JwtTokenRepository jwtTokenRepository) {
        this.jwtTokenRepository = jwtTokenRepository;
    }

    @Override
    public void create(JwtToken jwtToken) {
         jwtTokenRepository.save(jwtToken);
    }

    @Override
    public List<JwtToken> findAll() {
        return jwtTokenRepository.findAll();
    }

    @Override
    public void update(JwtToken jwtToken) {
        String token = jwtToken.getToken();
        jwtTokenRepository.update(jwtToken);
    }

    @Override
    public void delete(String token) {
        jwtTokenRepository.deleteByToken(token);
    }

    @Override
    public boolean invalidate(String token) {
        String id = token.replace("Bearer_", "");
        JwtToken jwtToken = jwtTokenRepository.findByToken(id)
                .orElseThrow(() -> new JwtTokenNotFoundException(NOT_FOUND_MESSAGE));
        if (jwtToken.getStatus().equals(TokenStatus.ACTIVE)) {
            jwtToken.setStatus(TokenStatus.INACTIVE);
            jwtTokenRepository.update(jwtToken);
            return true;
        }
        return false;
    }

    @Override
    public boolean isActive(String token) {
        JwtToken jwtToken = jwtTokenRepository.findByToken(parseToken(token))
                .orElseThrow(() -> new JwtTokenNotFoundException(NOT_FOUND_MESSAGE));
        return jwtToken.getStatus().equals(TokenStatus.ACTIVE);
    }

    @Override
    public void cleanup(Long expiredTimeInMilliseconds) {
        jwtTokenRepository.cleanup(expiredTimeInMilliseconds);
    }

    @Override
    public String parseToken(String token) {
        return token.replace("Bearer_", "");
    }
}
