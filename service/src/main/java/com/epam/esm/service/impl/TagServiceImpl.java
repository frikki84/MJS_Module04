package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.mapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private final TagRepository tagRepository;
    @Autowired
    private final TagDtoMapper mapper;

    public TagServiceImpl(TagRepository tagRepository, TagDtoMapper mapper) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Tag create(TagDto entity) {
        Tag tag = mapper.changeTagDtoToTag(entity);
        return tagRepository.create(tag);
    }

    @Override
    public Long delete(Long id) {
        return tagRepository.delete(id);
    }
}
