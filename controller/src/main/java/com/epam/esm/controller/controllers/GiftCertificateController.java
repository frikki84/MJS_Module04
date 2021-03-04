package com.epam.esm.controller.controllers;



import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.controller.util.PaginationBuilder;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftCertificateParameterDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/certificates")

public class GiftCertificateController {
    @Autowired
    private final GiftCertificateService giftCertificateService;
    private final HateoasBuilder hateoas;
    private final PaginationBuilder<GiftCertificateDto> pagination;

    public GiftCertificateController(GiftCertificateService giftCertificateService, HateoasBuilder hateoas, PaginationBuilder<GiftCertificateDto> pagination) {
        this.giftCertificateService = giftCertificateService;
        this.hateoas = hateoas;
        this.pagination = pagination;
    }
    @GetMapping
    public PagedModel<GiftCertificateDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<GiftCertificateDto> list = giftCertificateService.findAll(page, size);
        hateoas.addLinksToGiftCertificateList(list);
        return pagination.addPagination(list, page, size, giftCertificateService.findNumberOfEntities());
    }

    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.findById(id));
    }

    @GetMapping("/find")
    public PagedModel<GiftCertificateDto> findAllByParameter(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody SearchGiftCertificateParameterDto parameter) {
        List<GiftCertificateDto> list = giftCertificateService.findAll(parameter, page, size);
        hateoas.addLinksToGiftCertificateList(list);
        return pagination.addPagination(list, page, size, giftCertificateService.findNumberOfEntities());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GiftCertificateDto create(@RequestBody GiftCertificateDto dto) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.create(dto));
    }

    @DeleteMapping("/{id}")
    public long delete(@PathVariable long id) {
        return giftCertificateService.delete(id);
    }

    @PatchMapping("/{id}")
    public GiftCertificateDto update(@RequestBody GiftCertificateDto dto, @PathVariable Long id) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.update(dto, id));
    }
}
