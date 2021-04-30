package com.epam.esm.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.HateoasBuilder;
import com.epam.esm.util.PaginationBuilder;
import com.epam.esm.entity.SearchGiftCertificateParameterDto;

@RestController
@RequestMapping("/v3/certificates")
public class GiftCertificateController {

    public static final String DEFAULTE_PAGE_VALUE = "1";
    public static final String DEFAULTE_SIZE_VALUE = "10";
    public static final String AUTHORITY_READ = "hasAuthority('certificate:read')";
    public static final String AUTHORITY_WRITE = "hasAuthority('certificate:write')";

    @Autowired
    private final GiftCertificateService giftCertificateService;
    private final HateoasBuilder hateoasBuilder;
    private final PaginationBuilder<GiftCertificateDto> paginationBuilder;

    public GiftCertificateController(GiftCertificateService giftCertificateService, HateoasBuilder hateoas,
            PaginationBuilder<GiftCertificateDto> pagination) {
        this.giftCertificateService = giftCertificateService;
        this.hateoasBuilder = hateoas;
        this.paginationBuilder = pagination;
    }

    @GetMapping
    public PagedModel<GiftCertificateDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULTE_PAGE_VALUE) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULTE_SIZE_VALUE) int size) {
        List<GiftCertificateDto> list = giftCertificateService.findAll(page, size);
        hateoasBuilder.addLinksToGiftCertificateList(list);
        return paginationBuilder.addPagination(list, page, size, giftCertificateService.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    @PreAuthorize(AUTHORITY_READ)
    public GiftCertificateDto findById(@PathVariable Long id) {
        GiftCertificateDto dto = giftCertificateService.findById(id);
        return hateoasBuilder.addLinksToGiftCertificate(dto);
    }

    @GetMapping("/find")
    @PreAuthorize(AUTHORITY_READ)
    public PagedModel<GiftCertificateDto> findAllByParameter(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULTE_PAGE_VALUE) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULTE_SIZE_VALUE) int size,
            @RequestBody SearchGiftCertificateParameterDto parameter) {

        List<GiftCertificateDto> list = giftCertificateService.findAll(parameter, page, size);
        hateoasBuilder.addLinksToGiftCertificateList(list);
        return paginationBuilder.addPagination(list, page, size,
                giftCertificateService.findNumberOfEntities(parameter));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(AUTHORITY_WRITE)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto dto) {
        return hateoasBuilder.addLinksToGiftCertificate(giftCertificateService.create(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(AUTHORITY_WRITE)
    public long delete(@PathVariable long id) {
        return giftCertificateService.delete(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize(AUTHORITY_WRITE)
    public GiftCertificateDto update(@RequestBody GiftCertificateDto dto, @PathVariable Long id) {
        return hateoasBuilder.addLinksToGiftCertificate(giftCertificateService.update(dto, id));
    }
}
