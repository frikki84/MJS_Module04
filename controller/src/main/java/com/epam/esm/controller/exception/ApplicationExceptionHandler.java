package com.epam.esm.controller.exception;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.ExceptionDetails;
import com.epam.esm.service.exception.GiftCertificateDtoValidationException;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.PageException;
import com.epam.esm.service.exception.TagValidationException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    public static final String NO_SUCH_RESOURCE_MESSAGE = "no_resource";

    private final ReloadableResourceBundleMessageSource resourceBundle;

    public ApplicationExceptionHandler(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<ExceptionDetails> handleNoSuchResourceException(NoSuchResourceException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = getErrorResponse(NO_SUCH_RESOURCE_MESSAGE);
        String errorCode = status.value() + exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);

        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(TagValidationException.class)
    public ResponseEntity<ExceptionDetails> handleTagValidationException(TagValidationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = CustomErrorCode.TAG.getCode();
        String errorCode = status.value() + exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(GiftCertificateDtoValidationException.class)
    public ResponseEntity<ExceptionDetails> handleGiftCertificateDtoValidationException(
            GiftCertificateDtoValidationException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = CustomErrorCode.CERTIFICATE.getCode();
        String errorCode = status.value() + exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    @ExceptionHandler(PageException.class)
    public ResponseEntity<ExceptionDetails> handlePageException(PageException exception) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = CustomErrorCode.CERTIFICATE.getCode();
        String errorCode = status.value() + exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    private String getErrorResponse(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
        Locale locale = request.getLocale();
        String bundleMessage = resourceBundle.getMessage(key, new Object[] {}, locale);
        return bundleMessage;
    }

}
