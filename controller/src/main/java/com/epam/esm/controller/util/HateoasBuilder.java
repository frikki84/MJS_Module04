package com.epam.esm.controller.util;

import com.epam.esm.controller.controllers.OrderController;
import com.epam.esm.controller.controllers.GiftCertificateController;
import com.epam.esm.controller.controllers.TagController;
import com.epam.esm.controller.controllers.UserController;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.TagDto;
import com.epam.esm.entity.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class HateoasBuilder{
    public static final String CERTIFICATE_UPDATE = "update certificate";
    public static final String CERTIFICATE_DELETE = "delete certificate";
    public static final String CERTIFICATE_CREATE = "create certificate";
    public static final String TAG_DELETE = "delete tag";
    public static final String TAG_CREATE = "create tag";
    public static final String USER_DELETE = "delete user";
    public static final String USER_CREATE = "create user";

    public GiftCertificateDto addLinksToGiftCertificate(GiftCertificateDto dto) {
        dto.add(linkTo(methodOn(GiftCertificateController.class).update(dto, dto.getId()))
                .withRel(CERTIFICATE_UPDATE)
                .withType(HttpMethod.PATCH.name()));
        dto.add(linkTo(methodOn(GiftCertificateController.class).create(dto))
                .withRel(CERTIFICATE_CREATE)
                .withType(HttpMethod.POST.name()));
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
        tagDto.add(linkTo(methodOn(TagController.class).create(tagDto))
                .withRel(TAG_CREATE)
                .withType(HttpMethod.POST.name()));
        tagDto.add(linkTo(methodOn(TagController.class).delete(tagDto.getId()))
                .withRel(TAG_DELETE)
                .withType(HttpMethod.DELETE.name()));
        return tagDto;
    }

    public void addLinksToListTag(List<TagDto> tagDtoList) {
       tagDtoList.forEach(tag -> tag.add(linkTo(methodOn(TagController.class).findById(tag.getId()))
                .withSelfRel()
                .withType(HttpMethod.GET.name())));
    }

    public UserDto addLinksToUser(UserDto userDto) {
        userDto.add(linkTo(methodOn(UserController.class).create(userDto))
                .withRel(USER_CREATE)
                .withType(HttpMethod.POST.name()));
        userDto.add(linkTo(methodOn(UserController.class).delete(userDto.getId()))
                .withRel(USER_DELETE)
                .withType(HttpMethod.DELETE.name()));
        return userDto;
    }

    public void addLinksToListUser(List<UserDto> userDtoList) {
        userDtoList.forEach(userDto -> userDto.add(linkTo(methodOn(UserController.class).findById(userDto.getId()))
                .withSelfRel()
                .withType(HttpMethod.GET.name())));
    }
//
//    public UserDto addLinksToUserByOrder(UserDto user) {
//        user.add(linkTo(methodOn(OrderController.class).readByUserId(user.getId())).withRel("all user orders"));
//        return user;
//    }







}
