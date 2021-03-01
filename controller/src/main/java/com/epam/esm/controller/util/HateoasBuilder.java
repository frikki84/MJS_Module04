package com.epam.esm.controller.util;

import com.epam.esm.controller.controllers.GiftCertificateController;
import com.epam.esm.controller.controllers.TagController;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.TagDto;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HateoasBuilder <T>{
    public static final String CERTIFICATE_UPDATE = "update certificate";
    public static final String CERTIFICATE_DELETE = "delete certificate";
    public static final String TAG_DELETE = "delete tag";
    public static final String TAG_CREATE = "create tag";

    public PagedModel<T> addPagination(List<T> entities, int page, int size, long entitiesCount) {
        PagedModel.PageMetadata pageMetadata = new PagedModel.PageMetadata(size, page, entitiesCount);
        return PagedModel.of(entities, pageMetadata);
    }

    public GiftCertificateDto addLinksToGiftCertificate(GiftCertificateDto dto) {
        dto.add(linkTo(methodOn(GiftCertificateController.class).update(dto, dto.getId()))
                .withRel(CERTIFICATE_UPDATE)
                .withType(HttpMethod.PATCH.name()));
        dto.add(linkTo(methodOn(GiftCertificateController.class).delete(dto.getId()))
                .withRel(CERTIFICATE_DELETE)
                .withType(HttpMethod.DELETE.name()));
        addLinksToListTag(dto.getTagList());
        return dto;
    }

    public void addLinksToGiftCertificateList(List<GiftCertificateDto> certificateList) {
        certificateList.forEach(certificate -> {
            certificate.add(linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId()))
                    .withSelfRel()
                    .withType(HttpMethod.GET.name()));
            addLinksToListTag(certificate.getTagList());
        });
    }

    public TagDto addLinksToTag(TagDto tagDto) {
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId()))
                .withRel(TAG_DELETE)
                .withType(HttpMethod.DELETE.name()));
        tagDto.add(linkTo(methodOn(TagController.class).create(tagDto))
                .withRel(TAG_CREATE)
                .withType(HttpMethod.POST.name()));
        return tagDto;
    }

    public void addLinksToListTag(List<TagDto> tagDtoList) {
       tagDtoList.forEach(tag -> tag.add(linkTo(methodOn(TagController.class).findById(tag.getId()))
                .withSelfRel()
                .withType(HttpMethod.GET.name())));
    }




}
