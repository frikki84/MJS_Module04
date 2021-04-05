package com.epam.esm.service.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.epam.esm.entity.User;

public class SecurityUser implements UserDetails {

    public static final boolean DEFAULT_STATUS = true;

    private final String name;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUser(String name, String password, List<SimpleGrantedAuthority> authorities) {
        this.name = name;
        this.password = password;
        this.authorities = authorities;
    }

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
        return name;
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
        return true;
    }

    public static UserDetails createUserDetailsFromUser(User user) {
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                DEFAULT_STATUS, DEFAULT_STATUS, DEFAULT_STATUS, DEFAULT_STATUS, user.getRole().getAuthorities());

    }
}
