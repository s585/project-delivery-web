package tech.itpark.project_delivery_web.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import tech.itpark.project_delivery_web.model.Role;
import tech.itpark.project_delivery_web.model.enums.UserStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
public class CustomUserDetails implements UserDetails {

    private final Long id;
    private final String name;
    private final String password;
    private final String email;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;
    private final UserStatus status;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (status.equals(UserStatus.ACTIVE)) return true;
        return false;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public Long getId() {
        return id;
    }
}