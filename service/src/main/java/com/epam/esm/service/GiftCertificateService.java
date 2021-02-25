package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.CrdService;

public interface GiftCertificateService extends CrdService<GiftCertificateDto, Long> {
public GiftCertificateDto update(GiftCertificateDto giftCertificate, Long id);

}
