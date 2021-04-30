package com.epam.esm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.mapper.TagDtoMapper;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.validation.TagValidation;

@Service
@Transactional
public class TagService implements CrdService<TagDto> {

    @Autowired
    private final TagRepository tagRepository;
    private final TagDtoMapper mapper;
    private final UserRepository userRepository;
    private final TagValidation tagValidation;

    public TagService(TagRepository tagRepository, TagDtoMapper mapper, UserRepository userRepository,
            TagValidation tagValidation) {
        this.tagRepository = tagRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
        this.tagValidation = tagValidation;
    }

    @Override
    public List<TagDto> findAll(int offset, int limit) {
        return tagRepository.findAll(offset, limit)
                .stream()
                .map(tag -> mapper.changeTagToTagDto(tag))
                .collect(Collectors.toList());
    }

    @Override
    public TagDto findById(long id) {
        Tag tag = tagRepository.findById(id);
        if (Objects.isNull(tag)) {
            throw new NoSuchResourceException(CustomErrorCode.TAG);
        }
        return mapper.changeTagToTagDto(tag);
    }

    @Override
    public TagDto create(TagDto entity) {
        tagValidation.chechTagDtoFormat(entity);
        Tag tag = mapper.changeTagDtoToTag(entity);
        List<Tag> checkingTagList = tagRepository.findByName(entity.getName());
        Tag resultTag;
        if (checkingTagList.isEmpty()) {
            resultTag = tagRepository.create(tag);
        } else {
            resultTag = checkingTagList.get(0);
        }
        return mapper.changeTagToTagDto(resultTag);
    }

    @Override
    public long delete(long id) {
        Long tagId;
        try {
            tagId = tagRepository.delete(id);
        } catch (RuntimeException e) {
            throw new NoSuchResourceException(CustomErrorCode.TAG);
        }
        return tagId;
    }

    @Override
    public long findNumberOfEntities() {
        return tagRepository.findNumberOfEntities();
    }

    public List<TagDto> findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder() {
        Long userId = userRepository.findUserWithTheHighestCostOfAllOrder();
        List<Tag> tags = tagRepository.getMostWidelyUsedUsersTag(userId);
        List<TagDto> dtos = new ArrayList<>();
        tags.forEach(tag -> dtos.add(mapper.changeTagToTagDto(tag)));
        return dtos;
    }
}
