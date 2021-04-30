package com.epam.esm.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.configuration.IntParameterValues;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderCreationParameter;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.service.exception.AccessException;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.mapper.OrderDtoMapper;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.validation.PageInfoValidation;
import com.epam.esm.service.validation.SecurityValidator;

@Service
@Transactional
public class OrderService {

    @Autowired
    private final OrderRepository orderRepository;
    private final OrderDtoMapper orderMapper;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;
    private final PageInfoValidation pageInfoValidation;
    private final SecurityValidator securityValidator;

    public OrderService(OrderRepository orderRepository, OrderDtoMapper orderMapper, UserRepository userRepository,
            GiftCertificateRepository certificateRepository, PageInfoValidation pageInfoValidation,
            SecurityValidator securityValidator) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.pageInfoValidation = pageInfoValidation;
        this.securityValidator = securityValidator;
        pageInfoValidation.setCrdOperations(orderRepository);
    }

    public List<OrderDto> findAll(int offset, int limit) {
        pageInfoValidation.checkPageInfo(offset, limit, CustomErrorCode.ORDER);
        return orderRepository.findAll(offset, limit)
                .stream()
                .map(order -> orderMapper.chandeOrderToDto(order))
                .collect(Collectors.toList());
    }

    public OrderDto findById(long id) {
        Order order = orderRepository.findById(id);
        if (Objects.isNull(order)) {
            throw new NoSuchResourceException(CustomErrorCode.ORDER);
        }
        return orderMapper.chandeOrderToDto(order);
    }

    public OrderDto addOrder(OrderCreationParameter parameter) {
        User user = securityValidator.findUserFromAuthentication();
        if (parameter.getUserId() == IntParameterValues.DEFAULT_USER_ID_VALUE.getValue()) {
            parameter.setUserId(user.getId());
        }
        if (user.getRole().equals(Role.USER)) {
            parameter.setUserId(user.getId());
        }
        return create(parameter);
    }

    @Transactional
    public OrderDto create(OrderCreationParameter parameter) {
        User user = userRepository.findById(parameter.getUserId());
        if (Objects.isNull(user)) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        List<GiftCertificate> certificatesList = new ArrayList<>();
        BigDecimal price = BigDecimal.ZERO;
        for (Integer i : parameter.getCertificates()) {
            GiftCertificate certificate = certificateRepository.findById(i);
            if (Objects.isNull(certificate)) {
                throw new NoSuchResourceException(CustomErrorCode.ORDER);
            }
            price = price.add(certificate.getPrice());
            certificatesList.add(certificate);
        }
        Order order = new Order();
        order.setUser(user);
        order.setPrice(price);
        order.setDate(LocalDateTime.now());
        order.setGiftCertificateList(certificatesList);
        return orderMapper.chandeOrderToDto(orderRepository.create(order));
    }

    public long delete(long id) {
        Long findId;
        try {
            findId = orderRepository.delete(id);
        } catch (RuntimeException e) {
            throw new NoSuchResourceException(CustomErrorCode.ORDER);
        }
        return findId;
    }

    public long findNumberOfEntities() {
        return orderRepository.findNumberOfEntities();
    }

    public List<OrderDto> readOrdersByUser(Long userId) {
        User user = securityValidator.findUserFromAuthentication();
        if (Objects.isNull(userId)) {
            userId = user.getId();
        }
        System.out.println("userId = " + userId + ", user.getId() = " + user.getId());
        if (user.getRole().equals(Role.USER) && userId != user.getId()) {
            throw new AccessException(LocalizationExceptionMessageValues.INFORMATION_FOBBIDEN.getMessage());
        }
        List<Order> orderList = orderRepository.readOrdersByUser(userId);
        if ((Objects.isNull(orderList) || orderList.isEmpty()) && userId != user.getId()) {
            throw new NoSuchResourceException(CustomErrorCode.USER);
        }
        return orderList.stream().map(order -> orderMapper.chandeOrderToDto(order)).collect(Collectors.toList());
    }

}
