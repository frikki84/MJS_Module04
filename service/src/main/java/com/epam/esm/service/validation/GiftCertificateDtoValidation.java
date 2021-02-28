package com.epam.esm.service.validation;


import com.epam.esm.entity.GiftCertificateDto;
import com.epam.esm.service.exception.GiftCertificateDtoValidationException;

import java.math.BigDecimal;

public class GiftCertificateDtoValidation {
    public static final boolean DEFAULT_RESULT = true;

    public static final String INVALID_DTO_NAME = "dto_invalid_name";
    public static final String INVALID_DTO_DECRIPTION = "dto_invalid_description";
    public static final String INVALID_DTO_PRICE = "dto_invalid_price";
    public static final String INVALID_DTO_DURATION = "dto_invalid_duration";
    public static final String INVALID_DTO_TAG_LIST = "dto_invalid_taglist";

    public static boolean chechCertificateDtoFormat(GiftCertificateDto dto) {
        boolean result = DEFAULT_RESULT;
        if (dto.getName() == null || dto.getName().length() < 3 || dto.getName().length() >32) {
            throw new GiftCertificateDtoValidationException(INVALID_DTO_NAME);
        } else if (dto.getDescription() == null || dto.getDescription().length() < 5 || dto.getDescription().length() > 100) {
            throw new GiftCertificateDtoValidationException(INVALID_DTO_DECRIPTION);
        } else if (dto.getPrice() == null || dto.getPrice().compareTo(new BigDecimal("0")) < 0) {
            throw new GiftCertificateDtoValidationException(INVALID_DTO_PRICE);
        } else if (dto.getDuration() == null || dto.getDuration().compareTo(Integer.parseInt("1")) < 0){
            throw new GiftCertificateDtoValidationException(INVALID_DTO_DURATION);
        } else if (dto.getTagList() == null || dto.getTagList().size() == 0) {
            throw new GiftCertificateDtoValidationException(INVALID_DTO_TAG_LIST);
        }

        return  result;
    }

}
