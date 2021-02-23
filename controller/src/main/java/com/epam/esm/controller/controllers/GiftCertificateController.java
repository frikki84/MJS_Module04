package com.epam.esm.controller.controllers;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/certificates")
public class GiftCertificateController {
    @Autowired
    private final GiftCertificateRepository giftCertificateRepository;

    public GiftCertificateController(GiftCertificateRepository giftCertificateRepository) {
        this.giftCertificateRepository = giftCertificateRepository;
    }

    @GetMapping
    public List<GiftCertificate> findAll (){
        return giftCertificateRepository.findAll();

    }

    @GetMapping("/{id}")
    public GiftCertificate findById(@PathVariable Long id) {
        return giftCertificateRepository.findById(id);

    }

    @PostMapping
    public GiftCertificate create(@RequestBody GiftCertificate giftCertificate) {
        return giftCertificateRepository.create(giftCertificate);
    }
}
