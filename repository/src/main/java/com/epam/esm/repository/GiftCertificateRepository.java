package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.SearchGiftCertificateParameter;

import java.util.List;

public interface GiftCertificateRepository extends CrdOperations<GiftCertificate> {
    public List<GiftCertificate> findAll(SearchGiftCertificateParameter parametr, int offset, int limit);
    public GiftCertificate update(GiftCertificate giftCertificate);




}
