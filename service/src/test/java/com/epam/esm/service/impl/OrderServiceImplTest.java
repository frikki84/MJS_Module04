package com.epam.esm.service.impl;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.OrderDtoMapper;
import com.epam.esm.service.validation.PageInfoValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private  OrderRepository orderRepository;
    @Mock
    private  OrderDtoMapper orderMapper;
    @Mock
    private  UserRepository userRepository;
    @Mock
    private  GiftCertificateRepository certificateRepository;
    @Mock
    private  PageInfoValidation pageInfoValidation;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void findAll() {
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void findNumberOfEntities() {
    }

    @Test
    void readOrdersByUser() {
    }
}