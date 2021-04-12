package com.epam.esm.service.validation;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;

@Component
public class SecurityValidator {

    private String anonymousRole = "ROLE_ANONYMOUS";
    private final UserRepository userRepository;

    public SecurityValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> authority = (List<GrantedAuthority>) authentication.getAuthorities();
        String userName;
        if (authority.get(0).getAuthority().equals(anonymousRole)) {
            userName = (String) authentication.getPrincipal();
        } else {
            userName = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        }
        List<User> users = userRepository.findByName(userName);
        User user = null;
        if (Objects.nonNull(users) && !users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }

}
