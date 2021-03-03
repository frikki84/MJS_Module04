package com.epam.esm.service;

import com.epam.esm.entity.*;

import java.util.List;

public interface OrderService {
    public List<OrderDto> findAll(int offset, int limit);

    public OrderDto findById(long id);

    public OrderDto create(OrderCreationParameter parameter);

    public long delete(long id);

    public long findNumberOfEntities();

    List<OrderDto> readOrdersByUser(long userID);
}
