package tech.itpark.project_delivery_web.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.user.User;
import tech.itpark.project_delivery_web.security.jwt.JwtUser;
import tech.itpark.project_delivery_web.service.user.UserService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@Transactional
public class JwtUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.getByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        return JwtUser.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .authorities(mapToGrantedAuthorities(user.getRole()))
                .status(user.getStatus())
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
        Stream<SimpleGrantedAuthority> permissions = role.getPermissions()
                .stream().map(permission -> new SimpleGrantedAuthority(permission.getName()));

        return Stream.concat(Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName())),
                permissions).collect(Collectors.toList());
    }

}
