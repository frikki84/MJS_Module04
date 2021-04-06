package com.epam.esm.controller.controllers;

import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.controller.util.PaginationBuilder;
import com.epam.esm.entity.TagDto;
import com.epam.esm.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v3/tags")
public class TagController {

    public static final String DEFAULTE_PAGE_VALUE = "1";
    public static final String DEFAULTE_SIZE_VALUE = "10";
    public static final String AUTHORITY_READ = "hasAuthority('tag:read')";
    public static final String AUTHORITY_WRITE = "hasAuthority('tag:write')";

    @Autowired
    private final TagService tagService;
    private final HateoasBuilder hateoasBuilder;
    private final PaginationBuilder<TagDto> paginationBuilder;

    public TagController(TagService tagService, HateoasBuilder hateoas, PaginationBuilder<TagDto> pagination) {
        this.tagService = tagService;
        this.hateoasBuilder = hateoas;
        this.paginationBuilder = pagination;
    }

    @GetMapping
    @PreAuthorize(AUTHORITY_READ)
    public PagedModel<TagDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULTE_PAGE_VALUE) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULTE_SIZE_VALUE) int size) {
        List<TagDto> list = tagService.findAll(page, size);
        hateoasBuilder.addLinksToListTag(list);
        return paginationBuilder.addPagination(list, page, size, tagService.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    @PreAuthorize(AUTHORITY_READ)
    public TagDto findById(@PathVariable Long id) {
        return hateoasBuilder.addLinksToTag(tagService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(AUTHORITY_WRITE)
    public TagDto create(@RequestBody TagDto dto) {
        return hateoasBuilder.addLinksToTag(tagService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AUTHORITY_WRITE)
    public long delete(@PathVariable long id) {
        return tagService.delete(id);
    }

    @GetMapping("/most_used_tag")
    @PreAuthorize(AUTHORITY_WRITE)
    public TagDto findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder() {
        return tagService.findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder();
    }

}
