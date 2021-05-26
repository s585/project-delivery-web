package tech.itpark.project_delivery_web.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.getByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }

        return new JWTUser(user.getId(), user.getEmail(), user.getPassword(), user.getName(),
                user.getSurname(), user.getChief(), true, user.getCreationDate(),
                user.getChangingDate(), user.getFileName(), user.getStatus(), user.getDepartment(),
                user.getRole(), mapToGrantedAuthorities(user.getRole()));
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role userRole) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getName()));
        userRole.getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .forEach(authorities::add);
        return authorities;
    }
}
