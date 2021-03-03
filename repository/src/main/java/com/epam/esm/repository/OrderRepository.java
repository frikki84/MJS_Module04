package com.epam.esm.repository;

import com.epam.esm.entity.Order;

import java.util.List;

public interface OrderRepository extends CrdOperations<Order> {
    List<Order> readOrdersByUser(long userID);
}
