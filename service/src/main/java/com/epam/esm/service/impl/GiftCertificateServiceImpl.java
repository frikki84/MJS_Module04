package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.mapper.CertificateDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private final GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private final CertificateDtoMapper mapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, CertificateDtoMapper mapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.mapper = mapper;
    }

    @Override
    public List<GiftCertificate> findAll() {
        return giftCertificateRepository.findAll();
    }

    @Override
    public GiftCertificate findById(Long id) {
        return giftCertificateRepository.findById(id);
    }

    @Override
    public GiftCertificate create(GiftCertificateDto entity) {
        System.out.println("service create dto" + entity);
        GiftCertificate certificate = mapper.changeDtoToCertificate(entity);
        System.out.println("service create" + certificate);
        LocalDateTime currentDate = LocalDateTime.now();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return giftCertificateRepository.create(certificate);
    }

    @Override
    public Long delete(Long id) {
        return giftCertificateRepository.delete(id);
    }

    @Override
    public GiftCertificate update(GiftCertificateDto giftCertificate, Long id) {
        GiftCertificate certificate = mapper.changeDtoToCertificate(giftCertificate);
        GiftCertificate certificateFromDb = giftCertificateRepository.findById(id);
        if (certificate.getName() != null) {
            certificateFromDb.setName(certificate.getName());
        }
        if (certificate.getDescription() != null) {
            certificateFromDb.setDescription(certificate.getDescription());
        }
        if (certificate.getPrice() != null) {
            certificateFromDb.setPrice(certificate.getPrice());
        }
        if (certificate.getDuration() != null) {
            certificateFromDb.setDuration(certificate.getDuration());
        }
        if (certificate.getCreateDate() != null) {
            certificateFromDb.setCreateDate(certificate.getCreateDate());
        }

        if ( certificate.getTags()!= null || certificate.getTags().isEmpty())  {
            certificateFromDb.setTags(certificate.getTags());
        }

        LocalDateTime currentDate = LocalDateTime.now();
        certificateFromDb.setLastUpdateDate(currentDate);

        return giftCertificateRepository.update(certificateFromDb);
    }
}
