package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SearchGiftSertificateParametr;

import java.util.List;

public interface GiftCertificateRepository extends CrdOperations<GiftCertificate, Long> {
    public List<GiftCertificate> findAll(SearchGiftSertificateParametr parametr, int offset, int limit);
    public GiftCertificate update(GiftCertificate giftCertificate);




}
