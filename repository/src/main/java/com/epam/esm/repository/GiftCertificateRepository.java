package com.epam.esm.repository;

import com.epam.esm.entity.GiftCertificate;

public interface GiftCertificateRepository extends CrdOperations<GiftCertificate, Long> {
    public GiftCertificate update(GiftCertificate giftCertificate);



}
