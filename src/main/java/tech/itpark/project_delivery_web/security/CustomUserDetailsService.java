package tech.itpark.project_delivery_web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.security.jwt.CustomUserDetails;
import tech.itpark.project_delivery_web.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

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

        return CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .role(user.getRole())
                .authorities(mapToGrantedAuthorities(user.getRole()))
                .status(user.getStatus())
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
        return authorities;
    }
}
