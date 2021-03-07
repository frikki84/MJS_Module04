package com.epam.esm.controller.controllers;

import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.controller.util.PaginationBuilder;
import com.epam.esm.entity.OrderCreationParameter;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v2/orders")
public class OrderController {
    @Autowired
    private final OrderService orderService;
    private final HateoasBuilder hateoas;
    private final PaginationBuilder<OrderDto> pagination;

    public OrderController(OrderService orderService, HateoasBuilder hateoas, PaginationBuilder<OrderDto> pagination) {
        this.orderService = orderService;
        this.hateoas = hateoas;
        this.pagination = pagination;
    }


    @GetMapping
    public PagedModel<OrderDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<OrderDto> list = orderService.findAll(page, size);
        hateoas.addLinksToListOrder(list);
        return pagination.addPagination(list,page, size, orderService.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    public OrderDto findById(@PathVariable long id) {
        return hateoas.addLinksToOrder(orderService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto create(@RequestBody OrderCreationParameter parameter) {
        return hateoas.addLinksToOrder(orderService.create(parameter));
    }

    @DeleteMapping("/{id}")
    public long delete(@PathVariable long id) {
        return orderService.delete(id);
    }

    @GetMapping(params = "user")
    public List<OrderDto> readOrdersByUser(
            @RequestParam(value = "user", required = true) long userId) {
        List<OrderDto> orderDtoList = orderService.readOrdersByUser(userId);
        hateoas.addLinksToListOrder(orderDtoList);
        return  orderDtoList;
    }
}
