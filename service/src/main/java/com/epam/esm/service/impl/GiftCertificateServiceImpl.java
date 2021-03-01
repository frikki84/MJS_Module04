package com.epam.esm.service.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.entity.SearchGiftSertificateParametr;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.mapper.CertificateDtoMapper;
import com.epam.esm.service.validation.GiftCertificateDtoValidation;
import com.epam.esm.service.validation.PageInfoValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@Transactional
public class GiftCertificateServiceImpl implements GiftCertificateService {
    @Autowired
    private final GiftCertificateRepository giftCertificateRepository;
    @Autowired
    private final CertificateDtoMapper mapper;
    @Autowired
    private final PageInfoValidation pageValidation;


    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, CertificateDtoMapper mapper, PageInfoValidation validation, PageInfoValidation pageValidation) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.mapper = mapper;
        this.pageValidation = pageValidation;
    }

    @Override
    public List<GiftCertificateDto> findAll(int offset, int limit) {
        pageValidation.checkPageInfo(offset, limit);
        return giftCertificateRepository.findAll(offset, limit).stream()
                .map(giftCertificate -> mapper.changeCertificateToDto(giftCertificate))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findAll(SearchGiftSertificateParametr parametr, int offset, int limit) {
        pageValidation.checkPageInfo(offset, limit);
        if (Objects.isNull(parametr) || parametr.isEmpty()) {
            return findAll(offset, limit);
        }
        return giftCertificateRepository.findAll(parametr, offset, limit).stream()
                .map(giftCertificate -> mapper.changeCertificateToDto(giftCertificate))
                .collect(Collectors.toList());
    }

    @Override
    public long findNumberOfEntities() {
        return giftCertificateRepository.findNumberOfEntities();
    }

    @Override
    public GiftCertificateDto findById(Long id) {
        return mapper.changeCertificateToDto(giftCertificateRepository.findById(id));
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto entity) {
        GiftCertificateDtoValidation.chechCertificateDtoFormat(entity);
        GiftCertificate certificate = mapper.changeDtoToCertificate(entity);
        LocalDateTime currentDate = LocalDateTime.now();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return mapper.changeCertificateToDto(giftCertificateRepository.create(certificate));
    }

    @Override
    public Long delete(Long id) {
        return giftCertificateRepository.delete(id);
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificate, Long id) {
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

        if (certificate.getTags() != null || certificate.getTags().isEmpty()) {
            certificateFromDb.setTags(certificate.getTags());
        }

        LocalDateTime currentDate = LocalDateTime.now();
        certificateFromDb.setLastUpdateDate(currentDate);

        return mapper.changeCertificateToDto(giftCertificateRepository.update(certificateFromDb));
    }


}
