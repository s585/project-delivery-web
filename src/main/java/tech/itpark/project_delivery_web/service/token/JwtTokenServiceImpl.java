package tech.itpark.project_delivery_web.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.security.exception.JwtTokenNotFoundException;
import tech.itpark.project_delivery_web.model.JwtToken;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;
import tech.itpark.project_delivery_web.repository.JwtTokenRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class JwtTokenServiceImpl implements JwtTokenService {

    private static final String NOT_FOUND_MESSAGE = "Token not found";
    private static final long EXPIRATION_TIME = 2592000000L;

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
        jwtTokenRepository.save(jwtToken);
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
            jwtTokenRepository.save(jwtToken);
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
    public void cleanup() {
        LocalDateTime dateTime = LocalDateTime.now().minusSeconds(EXPIRATION_TIME);
        jwtTokenRepository.deleteJwtTokenByCreationDateBefore(dateTime);
    }

    @Override
    public String parseToken(String token) {
        return token.replace("Bearer_", "");
    }
}
