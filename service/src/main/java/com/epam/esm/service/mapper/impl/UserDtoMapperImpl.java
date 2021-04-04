package com.epam.esm.service.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.mapper.OrderDtoMapper;
import com.epam.esm.service.mapper.UserDtoMapper;

@Component
public class UserDtoMapperImpl implements UserDtoMapper {

//    private final OrderDtoMapper orderDtoMapper;
//
//    public UserDtoMapperImpl(OrderDtoMapper orderDtoMapper) {
//        this.orderDtoMapper = orderDtoMapper;
//    }

    @Override
    public User chandeDtoToUser(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
//        List<Order> orderList = new ArrayList<>();
//        dto.getOrderList().forEach(orderDto -> orderList.add(orderDtoMapper.chandeDtoToOrder(orderDto)));
//        user.setOrderList(orderList);
        return user;
    }

    @Override
    public UserDto chandeUserToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setRole(user.getRole());
        return dto;
    }

    public String changeRoleToString(UserDto user) {
        return user.getRole().name();
    }
}
