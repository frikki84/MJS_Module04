package com.epam.esm.entity;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
    ADMIN(Set.of(Permission.USER_READ, Permission.USER_WRITE, Permission.TAG_READ, Permission.TAG_WRITE,
            Permission.ORDER_READ, Permission.ORDER_WRITE, Permission.CERTIFICATE_READ, Permission.CERTIFICATE_WRITE)),

    USER(Set.of(Permission.CERTIFICATE_READ, Permission.TAG_READ, Permission.ORDER_READ));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
