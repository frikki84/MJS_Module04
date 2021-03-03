package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.entity.User;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.mapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TagServiceImpl implements TagService {

    @Autowired
    private final TagRepository tagRepository;
    private final TagDtoMapper mapper;
    private final UserRepository userRepository;

    public TagServiceImpl(TagRepository tagRepository, TagDtoMapper mapper, UserRepository userRepository) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<TagDto> findAll(int offset, int limit) {
        return tagRepository.findAll(offset, limit).stream().map(tag -> mapper.changeTagToTagDto(tag)).collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        return mapper.changeTagToTagDto(tagRepository.findById(id));
    }

    @Override
    public TagDto create(TagDto entity) {
        Tag tag = mapper.changeTagDtoToTag(entity);
        return mapper.changeTagToTagDto(tagRepository.create(tag));
    }

    @Override
    public long delete(long id) {
        return tagRepository.delete(id);
    }

    @Override
    public long findNumberOfEntities() {
        return tagRepository.findNumberOfEntities();
    }


    @Override
    public TagDto findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder() {
        Long userId = userRepository.findUserWithTheHighestCostOfAllOrder();
        Tag tag = tagRepository.getMostWidelyUsedUsersTag(userId);
        return mapper.changeTagToTagDto(tag);
    }
}
