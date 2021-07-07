package tech.itpark.project_delivery_web.service.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.dto.PasswordResetRequestDto;
import tech.itpark.project_delivery_web.dto.user.UserAuthDto;
import tech.itpark.project_delivery_web.dto.vendor.VendorAuthDto;
import tech.itpark.project_delivery_web.mappers.UserMapper;
import tech.itpark.project_delivery_web.mappers.VendorMapper;
import tech.itpark.project_delivery_web.model.JwtToken;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.enums.TokenStatus;
import tech.itpark.project_delivery_web.model.user.AuthUser;
import tech.itpark.project_delivery_web.model.user.User;
import tech.itpark.project_delivery_web.model.user.Vendor;
import tech.itpark.project_delivery_web.security.jwt.JwtTokenProvider;
import tech.itpark.project_delivery_web.security.jwt.JwtUser;
import tech.itpark.project_delivery_web.service.token.JwtTokenService;
import tech.itpark.project_delivery_web.service.user.UserService;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    private JwtTokenService jwtTokenService;
    private UserMapper userMapper;
    private VendorMapper vendorMapper;
    private UserService service;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder decoder) {
        this.encoder = decoder;
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
    public Map<String, Object> processRequest(UserAuthDto incomingData) {
        try {
            User user = new User();
            Authentication authentication = authenticate(incomingData.getEmail(), incomingData.getPassword());
            JwtUser principal = (JwtUser) authentication.getPrincipal();
            user.setId(principal.getId());
            user.setEmail(principal.getUsername());
            user.setName(principal.getName());
            user.setRole(principal.getRole());
            user.setPassword(principal.getPassword());
            user.setStatus(principal.getStatus());
            String jwt = createToken(user.getRole(), user.getEmail());
            Map<String, Object> responseMap = new HashMap<>();
            if (checkAuthorityPermission(user)) {
                this.saveGeneratedJwtToken(jwt, new Date(), TokenStatus.ACTIVE.name());
                responseMap.put("user", userMapper.toRegistrationDto(user));
                responseMap.put("token", jwt);
            } return responseMap;
        } catch (BadCredentialsException e) {
            throw new AccessDeniedException("Доступ запрещен");
        }
    }

    @Override
    public Map<String, Object> processRequest(VendorAuthDto incomingData) {
        Vendor vendor = new Vendor();
        Authentication authentication = authenticate(incomingData.getEmail(), incomingData.getPassword());

        JwtUser principal = (JwtUser) authentication.getPrincipal();
        vendor.setId(principal.getId());
        vendor.setEmail(principal.getUsername());
        vendor.setName(principal.getName());
        vendor.setRole(principal.getRole());
        vendor.setPassword(principal.getPassword());
        vendor.setStatus(principal.getStatus());

        String jwt = createToken(vendor.getRole(), vendor.getEmail());
        Map<String, Object> responseMap = new HashMap<>();
        if (checkAuthorityPermission(vendor)) {
            this.saveGeneratedJwtToken(jwt, new Date(), TokenStatus.ACTIVE.name());
            responseMap.put("user", vendorMapper.toRegistrationDto(vendor));
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
    public String createToken(Role role, String email) {
        return jwtTokenProvider.createToken(role, email);
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
    public boolean resetPassword(PasswordResetRequestDto dto) {
        User user = service.findByEmail(dto.getEmail());
        if (!encoder.matches(dto.getSecret(), user.getSecret()))
            throw new AccessDeniedException("Секреты не совпадают");
        user.setPassword(encoder.encode(dto.getPassword()));
        service.update(user);
        return true;
    }

    private boolean checkAuthorityPermission(AuthUser user) {
        List<String> roles = List.of("ADMIN", "USER", "VENDOR");
        return roles.contains(user.getRole().getName());
    }
}
