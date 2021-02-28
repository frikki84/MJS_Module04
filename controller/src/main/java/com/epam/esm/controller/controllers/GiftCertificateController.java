package com.epam.esm.controller.controllers;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftSertificateParametr;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/certificates")
public class GiftCertificateController {
    @Autowired
    private final GiftCertificateService giftCertificateService;

    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {

        return giftCertificateService.findAll(page, size);
    }

    @GetMapping("/find")
    public List<GiftCertificateDto> findAllByParameter(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestBody SearchGiftSertificateParametr parametr) {

        return giftCertificateService.findAll(parametr, page, size);
    }


    @GetMapping("/{id}")
    public GiftCertificateDto findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);

    }

    @PostMapping
    public GiftCertificateDto create(@RequestBody GiftCertificateDto dto) {
        return giftCertificateService.create(dto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return giftCertificateService.delete(id);
    }

    @PatchMapping
    public GiftCertificateDto update(@RequestBody GiftCertificateDto dto, Long id) {
        return giftCertificateService.update(dto, id);
    }
}
