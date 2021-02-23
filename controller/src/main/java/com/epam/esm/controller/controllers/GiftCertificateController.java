package com.epam.esm.controller.controllers;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
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
    public List<GiftCertificate> findAll (){
        return giftCertificateService.findAll();

    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        return giftCertificateService.findById(id);

    }

    @PostMapping
    public GiftCertificate create(@RequestBody GiftCertificateDto dto) {
        return giftCertificateService.create(dto);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        return giftCertificateService.delete(id);
    }

    @PatchMapping
    public GiftCertificate update(@RequestBody GiftCertificateDto dto, Long id) {
        return giftCertificateService.update(dto, id);
    }
}
