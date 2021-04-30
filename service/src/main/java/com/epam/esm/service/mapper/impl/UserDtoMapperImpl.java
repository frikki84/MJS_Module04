package com.epam.esm.service.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.ShownUserDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.mapper.UserDtoMapper;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    private final String defaultPassword = "***";

    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserDtoMapperImpl(PasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User chandeDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    @Override
    public ShownUserDto chandeUserToDto(User user) {
        ShownUserDto dto = new ShownUserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public User changeShownDtoToUser(ShownUserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(defaultPassword);
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    @Override
    public UserDto changeShownToSimpleDto(ShownUserDto shownUserDto) {
        UserDto userDto = new UserDto();
        userDto.setId(shownUserDto.getId());
        userDto.setName(shownUserDto.getName());
        userDto.setPassword(defaultPassword);
        userDto.setEmail(shownUserDto.getEmail());
        userDto.setRole(shownUserDto.getRole());
        return userDto;
    }

}
