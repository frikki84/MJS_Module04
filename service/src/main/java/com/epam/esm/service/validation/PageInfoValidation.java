package com.epam.esm.service.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.esm.repository.GiftCertificateRepository;
import com.epam.esm.service.exception.PageException;

@Component
public class PageInfoValidation {

    public static final int MIN_PAGE_VALUE = 0;
    public static final int MIN_PAGE_SIZE = 1;
    public static final int VALUE_TO_CHANGE_FIRST_INDEX_FROM_ONE_TO_ZERO = 1;

    public static final String INVALID_PAGE_NUMBER = "page_invalid_number";
    public static final String NONEXISTENT_PAGE_SIZE = "page_wrong_size";
    public static final String NONEXISTENT_PAGE_NUMBER = "page_wrong_page_number";

    @Autowired
    private final GiftCertificateRepository repository;

    public PageInfoValidation(GiftCertificateRepository repository) {
        this.repository = repository;
    }

    public boolean checkPageInfo(int offset, int limit) {
        boolean result = true;

        if (offset < MIN_PAGE_VALUE) {
            throw new PageException(INVALID_PAGE_NUMBER);
        }
        if (limit < MIN_PAGE_SIZE) {
            throw new PageException(NONEXISTENT_PAGE_SIZE);
        }
        if ((offset - VALUE_TO_CHANGE_FIRST_INDEX_FROM_ONE_TO_ZERO) * limit > repository.findNumberOfEntities()) {
            throw new PageException(NONEXISTENT_PAGE_NUMBER);
        }

        return result;
    }
}
