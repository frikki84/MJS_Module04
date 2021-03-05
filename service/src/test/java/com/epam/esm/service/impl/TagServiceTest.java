package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.mapper.TagDtoMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {
    public static final int OFFSET = 10;
    public static final int LIMIT = 10;

    @Mock
    private  TagRepository tagRepository;
    @Mock
    private  TagDtoMapper mapper;
    @Mock
    private  UserRepository userRepository;

    @Test
    void findAll() {
        Tag tag = new Tag();
        List<Tag> certificates = Stream.of(tag).collect(Collectors.toList());
        TagDto dto = new TagDto();
        List<TagDto> expected = Stream.of(dto).collect(Collectors.toList());
        //Mockito.when(re.findAll(OFFSET, LIMIT)).thenReturn(certificates);
        //Mockito.when(mapper.changeCertificateToDto(certificate)).thenReturn(certificateDTO);
       // assertEquals(expected, giftCertificateService.findAll(OFFSET, LIMIT));

    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }

    @Test
    void findNumberOfEntities() {
    }

    @Test
    void findMostWidelyUsedTagOfUserWithTheHighestCostOfAllOrder() {
    }
}