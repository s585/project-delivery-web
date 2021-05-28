package tech.itpark.project_delivery_web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import tech.itpark.project_delivery_web.model.User;
import tech.itpark.project_delivery_web.security.jwt.CustomUserDetails;
import tech.itpark.project_delivery_web.service.UserService;

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

        return new CustomUserDetails(user.getId(), user.getEmail(), user.getPassword(), user.getName(),
                user.getSecret(), mapToGrantedAuthorities(user.getRole()) , user.getStatus());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(String userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole));
        return authorities;
    }
}
