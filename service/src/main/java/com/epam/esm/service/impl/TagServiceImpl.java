package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.entity.User;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.mapper.TagDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
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
        Tag tag = tagRepository.findById(id);
        if (Objects.isNull(tag)) {
            throw new NoSuchResourceException(CustomErrorCode.TAG);
        }
        return mapper.changeTagToTagDto(tag);
    }

    @Override
    public TagDto create(TagDto entity) {
        Tag tag = mapper.changeTagDtoToTag(entity);
        List<Tag> checkingTagList = tagRepository.findByName(entity.getNameTag());
        Tag resultTag = null;
        if (Objects.isNull(checkingTagList) || checkingTagList.isEmpty()) {
            //nullable - варинаты проверки на null
            resultTag = tagRepository.create(tag);
        } else {
            resultTag = checkingTagList.get(0);
        }
        return mapper.changeTagToTagDto(tagRepository.create(resultTag));
    }

    @Override
    public long delete(long id) {
        Long tagId = null;
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


    @Override
    public TagDto findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder() {
        Long userId = userRepository.findUserWithTheHighestCostOfAllOrder();
        Tag tag = tagRepository.getMostWidelyUsedUsersTag(userId);
        return mapper.changeTagToTagDto(tag);
    }
}
