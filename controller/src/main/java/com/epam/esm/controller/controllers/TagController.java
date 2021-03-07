package com.epam.esm.controller.controllers;

import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.controller.util.PaginationBuilder;
import com.epam.esm.entity.TagDto;
import com.epam.esm.service.impl.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/tags")
public class TagController {
    @Autowired
    private final TagService tagService;
    private final HateoasBuilder hateoas;
    private final PaginationBuilder<TagDto> pagination;

    public TagController(TagService tagService, HateoasBuilder hateoas, PaginationBuilder<TagDto> pagination) {
        this.tagService = tagService;
        this.hateoas = hateoas;
        this.pagination = pagination;
    }

    @GetMapping
    public PagedModel<TagDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<TagDto> list = tagService.findAll(page, size);
        hateoas.addLinksToListTag(list);
        return pagination.addPagination(list,page, size, tagService.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    public TagDto findById(@PathVariable Long id) {
        return hateoas.addLinksToTag(tagService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TagDto create(@RequestBody TagDto dto) {
        return hateoas.addLinksToTag(tagService.create(dto));
    }

    @DeleteMapping("/{id}")
    public long delete(@PathVariable long id) {
        return tagService.delete(id);
    }

    @GetMapping("/most_used_tag")
    public TagDto findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder(){
        return tagService.findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder();
    }


}
