package com.epam.esm.service.mapper;

import com.epam.esm.entity.SearchGiftCertificateParameterDto;
import com.epam.esm.entity.SearchGiftCertificateParameter;

public interface SearchParamterDtoMapper {
    public SearchGiftCertificateParameter changeDtoToSearchGiftSertificateParametr(SearchGiftCertificateParameterDto dto);

}
