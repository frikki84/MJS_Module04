package com.epam.esm.controller.controllers;

import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.controller.util.PaginationBuilder;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v2/users")
public class UserController {
    @Autowired
    private UserService service;
    private HateoasBuilder hateoas;
    private final PaginationBuilder<UserDto>pagination;


    public UserController(UserService service, HateoasBuilder hateoasBuilder, PaginationBuilder<UserDto> pagination) {
        this.service = service;
        this.hateoas = hateoasBuilder;
        this.pagination = pagination;
    }

    @GetMapping
    public PagedModel<UserDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<UserDto> list = service.findAll(page, size);
        hateoas.addLinksToListUser(list);
        return pagination.addPagination(list, page, size, service.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    public UserDto findById(@PathVariable long id) {
        return hateoas.addLinksToUser(service.findById(id));

    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@RequestBody UserDto dto) {
        return hateoas.addLinksToUser(service.create(dto));
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return service.delete(id);
    }

}
