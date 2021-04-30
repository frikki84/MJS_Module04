package com.epam.esm.util;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.epam.esm.entity.ShownUserDto;
import com.epam.esm.controllers.GiftCertificateController;
import com.epam.esm.controllers.OrderController;
import com.epam.esm.controllers.TagController;
import com.epam.esm.controllers.UserController;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.OrderDto;
import com.epam.esm.entity.TagDto;
import com.epam.esm.entity.UserDto;
import com.epam.esm.service.mapper.UserDtoMapper;

@Component
public class HateoasBuilder {

    private String certificateUpdate = "update certificate";
    private String certificateCreate = "create certificate";
    private String tagCreate = "create tag";
    private String userCreate = "create user";
    private String orderLinkToUser = "user";
    private String orderUserOrders = "user orders";

    private final UserDtoMapper userDtoMapper;

    public HateoasBuilder(UserDtoMapper userDtoMapper) {
        this.userDtoMapper = userDtoMapper;
    }

    public GiftCertificateDto addLinksToGiftCertificate(GiftCertificateDto dto) {
        dto.add(WebMvcLinkBuilder.linkTo(methodOn(GiftCertificateController.class).update(dto, dto.getId())).withRel(certificateUpdate)
                .withType(HttpMethod.PATCH.name()));
        dto.add(linkTo(methodOn(GiftCertificateController.class).create(dto)).withRel(certificateCreate)
                .withType(HttpMethod.POST.name()));
        addLinksToListTag(dto.getTags());
        return dto;
    }

    public void addLinksToGiftCertificateList(List<GiftCertificateDto> certificateList) {
        certificateList.forEach(certificate -> {
            certificate.add(
                    linkTo(methodOn(GiftCertificateController.class).findById(certificate.getId())).withSelfRel()
                            .withType(HttpMethod.GET.name()));
            addLinksToListTag(certificate.getTags());
        });
    }

    public TagDto addLinksToTag(TagDto tagDto) {
        tagDto.add(WebMvcLinkBuilder.linkTo(methodOn(TagController.class).create(tagDto)).withRel(tagCreate)
                .withType(HttpMethod.POST.name()));

        return tagDto;
    }

    public void addLinksToListTag(List<TagDto> tagDtoList) {
        tagDtoList.forEach(tag -> tag.add(linkTo(methodOn(TagController.class).findById(tag.getId())).withSelfRel()
                .withType(HttpMethod.GET.name())));
    }

    public ShownUserDto addLinksToUser(ShownUserDto userDto) {
        UserDto dto = userDtoMapper.changeShownToSimpleDto(userDto);
        userDto.add(WebMvcLinkBuilder.linkTo(methodOn(UserController.class).create(dto)).withRel(userCreate)
                .withType(HttpMethod.POST.name()));
        return userDto;
    }

    public void addLinksToListUser(List<ShownUserDto> userDtoList) {
        userDtoList.forEach(userDto -> userDto.add(
                linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel()
                        .withType(HttpMethod.GET.name())));
    }

    public OrderDto addLinksToOrder(OrderDto orderDto) {
        addLinksToGiftCertificateList(orderDto.getCertificates());
        ShownUserDto user = orderDto.getUser();
        user.add(linkTo(methodOn(UserController.class).findById(user.getId())).withRel(orderLinkToUser)
                .withType(HttpMethod.GET.name()));
        return orderDto;
    }

    public void addLinksToListOrder(List<OrderDto> orderDtoList) {
        for (OrderDto orderDto : orderDtoList) {
            orderDto.add(WebMvcLinkBuilder.linkTo(methodOn(OrderController.class).readOrdersByUser(orderDto.getUser().getId())).withRel(
                    orderUserOrders).withType(HttpMethod.GET.name()));
            addLinksToGiftCertificateList(orderDto.getCertificates());
            ShownUserDto user = orderDto.getUser();
            user.add(linkTo(methodOn(UserController.class).findById(user.getId())).withRel(orderLinkToUser)
                    .withType(HttpMethod.GET.name()));
        }
    }
}
