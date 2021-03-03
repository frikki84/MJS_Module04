package com.epam.esm.service.mapper.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.mapper.OrderDtoMapper;
import com.epam.esm.service.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {

    @Override
    public User chandeDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        return user;
    }

    @Override
    public UserDto chandeUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        return dto;
    }
}
