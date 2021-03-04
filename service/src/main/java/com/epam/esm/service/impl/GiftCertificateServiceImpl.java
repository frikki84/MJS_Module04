package com.epam.esm.service.impl;

import com.epam.esm.entity.*;
import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.mapper.CertificateDtoMapper;
import com.epam.esm.service.mapper.SearchParamterDtoMapper;
import com.epam.esm.service.mapper.TagDtoMapper;
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
    private final CertificateDtoMapper mapper;
    private final PageInfoValidation pageValidation;
    private final TagRepository tagRepository;
    private final SearchParamterDtoMapper paramterDtoMapper;

    public GiftCertificateServiceImpl(GiftCertificateRepository giftCertificateRepository, CertificateDtoMapper mapper, PageInfoValidation validation, PageInfoValidation pageValidation, TagDtoMapper tagDtoMapper, TagRepository tagRepository, SearchParamterDtoMapper paramterDtoMapper) {
        this.giftCertificateRepository = giftCertificateRepository;
        this.mapper = mapper;
        this.pageValidation = pageValidation;
        this.tagRepository = tagRepository;
        this.paramterDtoMapper = paramterDtoMapper;
    }

    @Override
    public List<GiftCertificateDto> findAll(int offset, int limit) {
        pageValidation.checkPageInfo(offset, limit);
        return giftCertificateRepository.findAll(offset, limit).stream()
                .map(giftCertificate -> mapper.changeCertificateToDto(giftCertificate))
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findAll(SearchGiftCertificateParameterDto parametrDto, int offset, int limit) {
        pageValidation.checkPageInfo(offset, limit);
        if (Objects.isNull(parametrDto) || parametrDto.isEmpty()) {
            return findAll(offset, limit);
        }
        SearchGiftCertificateParameter parametr = paramterDtoMapper.changeDtoToSearchGiftSertificateParametr(parametrDto);
        return giftCertificateRepository.findAll(parametr, offset, limit).stream()
                .map(giftCertificate -> mapper.changeCertificateToDto(giftCertificate))
                .collect(Collectors.toList());
    }

    @Override
    public long findNumberOfEntities() {
        return giftCertificateRepository.findNumberOfEntities();
    }

    @Override
    public GiftCertificateDto findById(long id) {
        GiftCertificate certificate = giftCertificateRepository.findById(id);
        if (Objects.isNull(certificate)) {
            throw  new NoSuchResourceException(CustomErrorCode.CERTIFICATE);
        }
        return mapper.changeCertificateToDto(certificate);
    }

    @Override
    public GiftCertificateDto create(GiftCertificateDto entity) {
        GiftCertificateDtoValidation.chechCertificateDtoFormat(entity);
        GiftCertificate certificate = mapper.changeDtoToCertificate(entity);
        LocalDateTime currentDate = LocalDateTime.now();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        createNeccesaryTags(certificate);
        return mapper.changeCertificateToDto(giftCertificateRepository.create(certificate));
    }

    @Override
    public long delete(long id) {
        Long givenId = giftCertificateRepository.delete(id);
        if (Objects.isNull(givenId)) {
            throw new NoSuchResourceException(CustomErrorCode.CERTIFICATE);
        }
        return givenId;
    }

    @Override
    public GiftCertificateDto update(GiftCertificateDto giftCertificate, Long id) {
        GiftCertificate certificateFromDb = giftCertificateRepository.findById(id);
        if (Objects.isNull(certificateFromDb)) {
            throw new NoSuchResourceException(CustomErrorCode.CERTIFICATE);
        }
        GiftCertificate certificate = mapper.changeDtoToCertificate(giftCertificate);
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

    private void createNeccesaryTags(GiftCertificate giftCertificate) {
        List<Tag> tags = giftCertificate.getTags();
        if (Objects.nonNull(tags)) {
            tags.forEach(tag ->  {
                if (tagRepository.findById(tag.getId()) == null) {
                    tagRepository.create(tag);
                }
            });
        };
    }


}
