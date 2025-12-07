package com.example.ems.Security;

import com.example.ems.Model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final User user;
    public CustomUserDetails(User user) {
        this.user = Objects.requireNonNull(user, "user must not be null");
    }

    public User getUser() { return this.user; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Null-safe: return empty list if roles are null
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return Collections.emptyList();
        }

        return user.getRoles().stream()
                .filter(Objects::nonNull)
                .map(r -> new SimpleGrantedAuthority(
                        r.getName() == null ? "" : r.getName()))
                .collect(Collectors.toList());
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getUsername(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
