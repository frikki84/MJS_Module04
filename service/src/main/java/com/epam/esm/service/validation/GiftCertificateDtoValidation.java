package com.epam.esm.service.validation;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.epam.esm.configuration.IntParameterValues;
import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.exception.GiftCertificateDtoValidationException;
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;

@Component
public class GiftCertificateDtoValidation {

    private static TagValidation tagValidation;

    public GiftCertificateDtoValidation(TagValidation tagValidation) {
        this.tagValidation = tagValidation;
    }

    public static void chechCertificateDtoFormat(GiftCertificateDto dto) {
        if (Objects.isNull(dto.getName()) || dto.getName().isBlank()
                || dto.getName().length() < IntParameterValues.MIN_NAME_LENGTH.getValue()
                || dto.getName().length() > IntParameterValues.MAX_NAME_LENGTH.getValue()) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_NAME.getMessage());
        }
        if (Objects.isNull(dto.getDescription()) || dto.getDescription().isBlank()
                || dto.getDescription().length() < IntParameterValues.MIN_DESCRIPTION_LENGTH.getValue()
                || dto.getDescription().length() > IntParameterValues.MAX_DESCRIPTION_LENGTH.getValue()) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_DECRIPTION.getMessage());
        }
        if (Objects.isNull(dto.getPrice())
                || dto.getPrice().compareTo(new BigDecimal(IntParameterValues.MIN_PRICE.getValue())) < 0) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_PRICE.getMessage());
        }
        if (Objects.isNull(dto.getDuration())
                || dto.getDuration().compareTo(IntParameterValues.MIN_DURATION.getValue()) < 0) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_DURATION.getMessage());
        }
        if (Objects.isNull(dto.getTagList()) || dto.getTagList().isEmpty()) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_TAG_LIST.getMessage());
        }
        if (Objects.nonNull(dto.getTagList()) && !dto.getTagList().isEmpty()) {
            dto.getTagList().forEach(tagDto -> tagValidation.chechTagDtoFormat(tagDto));
        }

    }

    public void chechCertificateDtoFormatForUpdate(GiftCertificateDto dto) {

        if (Objects.nonNull(dto.getName()) && (dto.getName().length() < IntParameterValues.MIN_NAME_LENGTH.getValue()
                || dto.getName().length() > IntParameterValues.MAX_NAME_LENGTH.getValue())) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_NAME.getMessage());
        }
        if (Objects.nonNull(dto.getDescription()) && (
                dto.getDescription().length() < IntParameterValues.MIN_DESCRIPTION_LENGTH.getValue()
                        || dto.getDescription().length() > IntParameterValues.MAX_DESCRIPTION_LENGTH.getValue())) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_DECRIPTION.getMessage());
        }
        if (Objects.nonNull(dto.getPrice())
                && dto.getPrice().compareTo(new BigDecimal(IntParameterValues.MIN_PRICE.getValue())) < 0) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_PRICE.getMessage());
        }
        if (Objects.nonNull(dto.getDuration())
                && dto.getDuration().compareTo(IntParameterValues.MIN_DURATION.getValue()) < 0) {
            throw new GiftCertificateDtoValidationException(
                    LocalizationExceptionMessageValues.INVALID_DTO_DURATION.getMessage());
        }
        if (Objects.nonNull(dto.getTagList()) && !dto.getTagList().isEmpty()) {
            dto.getTagList().forEach(tagDto -> tagValidation.chechTagDtoFormat(tagDto));
        }
    }

}
