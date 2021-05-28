package tech.itpark.project_delivery_web.service.authentication;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tech.itpark.project_delivery_web.dto.user.PasswordRecoverDto;
import tech.itpark.project_delivery_web.dto.user.UserDtoAuth;
import tech.itpark.project_delivery_web.mappers.UserMapper;
import tech.itpark.project_delivery_web.model.JwtToken;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;
import tech.itpark.project_delivery_web.security.jwt.JwtTokenProvider;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;
import tech.itpark.project_delivery_web.service.user.UserService;
import tech.itpark.project_delivery_web.service.user.UserServiceImpl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenService jwtTokenService;
    private UserMapper userMapper;
    private UserService service;
    private BCryptPasswordEncoder decoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder decoder) {
        this.decoder = decoder;
    }

    @Autowired
    public void setService(UserService service) {
        this.service = service;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Autowired
    public void setJwtTokenService(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

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
    public String createToken(Authentication authentication, Role role, String email) {
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

    @Override
    public boolean recoverPassword(PasswordRecoverDto dto) {
        User user = service.getByEmail(dto.getEmail());
        if (!user.getSecret().equals(decoder.encode(dto.getSecret()))) throw new AccessDeniedException("!!!!!!!!!");
        user.setPassword(decoder.encode(dto.getPassword()));
        service.update(userMapper.toDto(user));
        return true;
    }

    private boolean checkAuthorityPermission(User user) {
        return user.getRole().getName().equals("ADMIN");
    }
}
