package com.epam.esm.service.exception;

public enum LocalizationExceptionMessageValues {
    INVALID_DTO_NAME("dto_invalid_name"), INVALID_DTO_DECRIPTION("dto_invalid_description"), INVALID_DTO_PRICE(
            "dto_invalid_price"), INVALID_DTO_DURATION("dto_invalid_duration"), INVALID_DTO_TAG_LIST(
            "dto_invalid_taglist"), INVALID_PAGE_NUMBER("page_invalid_number"), NONEXISTENT_PAGE_SIZE(
            "page_wrong_size"), NONEXISTENT_PAGE_NUMBER("page_wrong_page_number"), INVALID_TAG_NAME(
            "tagdto_invalid_name"), INVALID_USER_NAME("userdto_invalid_name"), INVALID_EMAIL(
            "userdto_invalid_email"), INVALID_PASSWORD("userdto_invalid_password"), USER_NAME_EXISTS(
            "username_exists"), NO_SUCH_RESOURCE_MESSAGE("no_resource"), NO_METHOD("no_method"), BAD_REQUEST(
            "bad_request"), WRONG_URL("wrong_url"), EXCEPTION_JWT_FILTER_MESSAGE("jwt_exception_filter"), JWT_EXCEPTION(
            "jwt_exception"), INFORMATION_FOBBIDEN("information_fobbiden");

    private String message;

    LocalizationExceptionMessageValues(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
