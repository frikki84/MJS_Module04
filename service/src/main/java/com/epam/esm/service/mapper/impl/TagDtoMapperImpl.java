package com.epam.esm.service.mapper.impl;


import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.TagDto;
import com.epam.esm.service.mapper.TagDtoMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagDtoMapperImpl implements TagDtoMapper {
    @Override
    public Tag changeTagDtoToTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setId(tagDto.getId());
        tag.setNameTag(tagDto.getNameTag());
        return tag;
    }


    @Override
    public List<Tag> changeCertificateDtoToTagList(GiftCertificateDto dto) {
        return dto.getTagList();
    }
}
