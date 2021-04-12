package com.epam.esm.configuration;

public enum IntParameterValues {
    OFFSET_DEFAULT_VALUE (1),
    POSITION_WITH_MAX_VALUE(1),
    DEFAULT_USER_ID_VALUE(0),
    MIN_NAME_LENGTH(3),
    MAX_NAME_LENGTH(32),
    MIN_DESCRIPTION_LENGTH(5),
    MAX_DESCRIPTION_LENGTH(128),
    MIN_PRICE(0),
    MIN_DURATION(1),
    MIN_PAGE_VALUE(1),
    MIN_PAGE_SIZE(1),
    VALUE_TO_CHANGE_FIRST_INDEX_FROM_ONE_TO_ZERO(1),
    MIN_TAG_LENGTH(3),
    MAX_TAG_LENGTH(16),
    MIN_USER_NAME_LENGTH(3),
    MAX_USER_NAME_LENGTH(32)






    ;

    private int value;

    private IntParameterValues(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
