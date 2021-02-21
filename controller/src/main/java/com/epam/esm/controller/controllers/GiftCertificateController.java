package com.epam.esm.controller.controllers;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Iterable<GiftCertificate> findAll(){
        System.out.println("I'm in controller");
        List<GiftCertificate> list = (List<GiftCertificate>) giftCertificateRepository.findAll();
        System.out.println(list);
        return list;
    }

    @GetMapping("/hello")
    public String returnString() {
        return "My world";
    }
}
