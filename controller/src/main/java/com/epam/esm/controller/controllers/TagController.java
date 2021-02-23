package com.epam.esm.controller.controllers;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v2/tags")
public class TagController {
    @Autowired
    private final TagRepository tagRepository;

    public TagController(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable Long id) {
        return tagRepository.findById(id);
    }

    @PostMapping
    public Tag create(@RequestBody Tag tag) {
        return tagRepository.create(tag);
    }


}
