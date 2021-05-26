package tech.itpark.project_delivery_web.security.jwt;

import andersen.rnm.entity.Department;
import andersen.rnm.entity.Role;
import andersen.rnm.entity.User;
import andersen.rnm.entity.status.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public class JWTUser implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final String name;
    private final String surname;
    private final User chief;
    private final boolean enabled;
    private final Date creationDate;
    private final Date changingDate;
    private final String fileName;
    private final UserStatus status;
    private final Department department;
    private final Role role;
    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getChangingDate() {
        return changingDate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getEmail() {
        return email;
    }

    public User getChief() {
        return chief;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getFileName() {
        return fileName;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Department getDepartment() {
        return department;
    }

    public Role getRole() {
        return role;
    }
}
