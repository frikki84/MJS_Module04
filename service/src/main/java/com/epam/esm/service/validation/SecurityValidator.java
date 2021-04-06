package com.epam.esm.service.validation;

import java.util.List;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;

@Component
public class SecurityValidator {

    private final UserRepository userRepository;

    public SecurityValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserFromAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName;
        try {
            userName = ((org.springframework.security.core.userdetails.User) authentication.getPrincipal()).getUsername();
        } catch (ClassCastException e) {
            userName = (String) authentication.getPrincipal();
        }
        List<User> users = userRepository.findByName(userName);
        User user = null;
        if (Objects.nonNull(users) && !users.isEmpty()) {
            user = users.get(0);
        }

        return user;
    }

}
