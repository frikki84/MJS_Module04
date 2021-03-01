package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftSertificateParametr;
import com.epam.esm.service.CrdService;

import java.util.List;

public interface GiftCertificateService extends CrdService<GiftCertificateDto, Long> {

    public GiftCertificateDto update(GiftCertificateDto giftCertificate, Long id);
    public List<GiftCertificateDto> findAll(SearchGiftSertificateParametr parametr, int offset, int limit);
    public long findNumberOfEntities();


}
