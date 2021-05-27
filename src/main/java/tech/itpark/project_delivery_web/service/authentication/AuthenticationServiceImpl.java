package tech.itpark.project_delivery_web.service.authentication;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.model.JwtToken;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;
import tech.itpark.project_delivery_web.security.jwt.JwtTokenProvider;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenService jwtTokenService;

    @Override
    public Map<String, Object> processRequest(UserDtoAuth incomingData) {
        User user = new User();
        Authentication authentication = authenticate(incomingData.getEmail(), incomingData.getPassword());
        BeanUtils.copyProperties(authentication.getPrincipal(), user);
        String jwt = createToken(authentication, user.getRole(), user.getEmail());
        Map<String, Object> responseMap = new HashMap<>();
        if (checkAuthorityPermission(user)) {
            this.saveGeneratedJwtToken(jwt, new Date(), TokenStatus.ACTIVE.name());
            responseMap.put("user", userMapper.toDto(user));
            responseMap.put("token", jwt);
            return responseMap;
        }
        throw new AccessDeniedException("Доступ запрещен");
    }

    @Override
    public String getEmail(String token) {
        return jwtTokenProvider.getUsername(token.replace("Bearer_", ""));
    }

    @Override
    public Authentication authenticate(String email, String password) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }

    @Override
    public String createToken(Authentication authentication, String role, String email) {
        return jwtTokenProvider.createToken(authentication, role, email);
    }

    @Override
    public void saveGeneratedJwtToken(String jwt, Date date, String status) {
        JwtToken token = new JwtToken();
        token.setToken(jwt);
        token.setCreationDate(LocalDateTime.now());
        token.setStatus(TokenStatus.ACTIVE);
        jwtTokenService.create(token);
    }

    private boolean checkAuthorityPermission(User user) {
        return user.getRole().equals("ADMIN");
    }
}
