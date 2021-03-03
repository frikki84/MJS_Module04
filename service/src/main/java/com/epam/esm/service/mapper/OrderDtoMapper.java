package com.epam.esm.service.mapper;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserDto;

public interface OrderDtoMapper {
    public Order chandeDtoToOrder(OrderDto dto);
    public OrderDto chandeOrderToDto(Order order);

}

