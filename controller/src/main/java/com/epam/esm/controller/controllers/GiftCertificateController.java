package com.epam.esm.controller.controllers;



import com.epam.esm.controller.util.HateoasBuilder;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftSertificateParametr;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/certificates")
public class GiftCertificateController {
    @Autowired
    private final GiftCertificateService giftCertificateService;
    @Autowired
    private final HateoasBuilder<GiftCertificateDto> hateoas;

    public GiftCertificateController(GiftCertificateService giftCertificateService, HateoasBuilder<GiftCertificateDto> hateoas) {
        this.giftCertificateService = giftCertificateService;
        this.hateoas = hateoas;
    }

    @GetMapping
    public PagedModel<GiftCertificateDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        List<GiftCertificateDto> list = giftCertificateService.findAll(page, size);
        hateoas.addLinksToGiftCertificateList(list);
        return hateoas.addPagination(list, page, size, giftCertificateService.findNumberOfEntities());
    }

    @GetMapping("/find")
    public PagedModel<GiftCertificateDto> findAllByParameter(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody SearchGiftSertificateParametr parametr) {
        List<GiftCertificateDto> list = giftCertificateService.findAll(parametr, page, size);
        hateoas.addLinksToGiftCertificateList(list);
        return hateoas.addPagination(list, page, size, giftCertificateService.findNumberOfEntities());
    }



    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable final Long id) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.findById(id));

    }

    @PostMapping
    public GiftCertificateDto create(@RequestBody GiftCertificateDto dto) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.create(dto));
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return giftCertificateService.delete(id);
    }

    @PatchMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto dto, Long id) {
        return hateoas.addLinksToGiftCertificate(giftCertificateService.update(dto, id));
    }
}
