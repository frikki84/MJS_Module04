package com.epam.esm.controller.exception;


import com.epam.esm.service.exception.*;
import com.epam.esm.service.exception.notused.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;


@ControllerAdvice
public class ApplicationExceptionHandler {
    public static final String NO_SUCH_RESOURCE_MESSAGE = "no_resource";
    public static final String TAG_EXISTS_MESSAGE = "tag_exist";
    public static final String NO_CERTIFICATE = "no_certificate";
    public static final String WRONG_TAG_NAME = "wrong_tag_name";
    public static final String NO_TAG_IN_DB = "no_tag_in_db";



    private final ReloadableResourceBundleMessageSource resourceBundle;

    public ApplicationExceptionHandler(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler({InvalidDataException.class})
    public ResponseEntity<ExceptionDetails> handleInvalidDataException(InvalidDataException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorCode = status.value() + exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), exception.getMessage(), errorCode);
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler({NoUserTag.class})
    public ResponseEntity<ExceptionDetails> handlerGeneralException(NoUserTag exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = getErrorResponse(NO_TAG_IN_DB);
        String errorCode = status.value() + exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<ExceptionDetails> handleNoSuchResourceException(NoSuchResourceException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = getErrorResponse(NO_SUCH_RESOURCE_MESSAGE);
        String errorCode = status.value()+ exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now()
                , status.value(), message, errorCode);

        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(NoCertificatesWithName.class)
    public ResponseEntity<ExceptionDetails> handleNoCertificatesWithName(NoCertificatesWithName exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = getErrorResponse(NO_CERTIFICATE);
        String errorCode = status.value()+ exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now()
                , status.value(), message, errorCode);

        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(TagValidationException.class)
    public ResponseEntity<ExceptionDetails> handleTagValidationException(TagValidationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(WRONG_TAG_NAME);
        String errorCode = status.value()+ exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now()
                , status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(GiftCertificateDtoValidationException.class)
    public ResponseEntity<ExceptionDetails> handleGiftCertificateDtoValidationException(GiftCertificateDtoValidationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = CustomErrorCode.CERTIFICATE.getCode();
        String errorCode = status.value()+ exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now()
                , status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }


    @ExceptionHandler(PageException.class)
    public ResponseEntity<ExceptionDetails> handlePageException(PageException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = CustomErrorCode.CERTIFICATE.getCode();
        String errorCode = status.value()+ exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now()
                , status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }


    private String getErrorResponse(String key) {
        HttpServletRequest request = ((ServletRequestAttributes)Objects
                .requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        Locale locale = request.getLocale();
        String bundleMessage = resourceBundle.getMessage(key, new Object[]{}, locale);

        return bundleMessage;
    }


}
