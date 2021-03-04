package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftCertificateParameterDto;

import java.util.List;

public interface GiftCertificateService extends CrdService<GiftCertificateDto> {
    public List<GiftCertificateDto> findAll(int offset, int limit);

    public GiftCertificateDto findById(long id);

    public GiftCertificateDto create(GiftCertificateDto entity);

    public long delete(long id);

    public GiftCertificateDto update(GiftCertificateDto giftCertificate, Long id);

    public List<GiftCertificateDto> findAll(SearchGiftCertificateParameterDto parametrDto, int offset, int limit);


}
