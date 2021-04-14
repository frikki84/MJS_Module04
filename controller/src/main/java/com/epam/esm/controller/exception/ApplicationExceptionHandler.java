package com.epam.esm.controller.exception;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.epam.esm.service.exception.AccessException;
import com.epam.esm.service.exception.CustomErrorCode;
import com.epam.esm.service.exception.CustomErrorExeption;
import com.epam.esm.service.exception.ExceptionDetails;
import com.epam.esm.service.exception.GiftCertificateDtoValidationException;
import com.epam.esm.service.exception.JwtAuthenticationException;
import com.epam.esm.service.exception.LocalizationExceptionMessageValues;
import com.epam.esm.service.exception.NoSuchResourceException;
import com.epam.esm.service.exception.PageException;
import com.epam.esm.service.exception.TagValidationException;
import com.epam.esm.service.exception.UserValidationException;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private final ReloadableResourceBundleMessageSource resourceBundle;

    public ApplicationExceptionHandler(ReloadableResourceBundleMessageSource resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @ExceptionHandler(NoSuchResourceException.class)
    public ResponseEntity<ExceptionDetails> handleNoSuchResourceException(NoSuchResourceException exception) {
        return createResponseEntity(HttpStatus.NOT_FOUND, exception,
                LocalizationExceptionMessageValues.NO_SUCH_RESOURCE_MESSAGE.getMessage());
    }

    @ExceptionHandler(TagValidationException.class)
    public ResponseEntity<ExceptionDetails> handleTagValidationException(TagValidationException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception, CustomErrorCode.TAG);
    }

    @ExceptionHandler(UserValidationException.class)
    public ResponseEntity<ExceptionDetails> handleUserValidationException(UserValidationException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception, CustomErrorCode.USER);
    }

    @ExceptionHandler(GiftCertificateDtoValidationException.class)
    public ResponseEntity<ExceptionDetails> handleGiftCertificateDtoValidationException(
            GiftCertificateDtoValidationException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception, CustomErrorCode.CERTIFICATE);
    }

    @ExceptionHandler(PageException.class)
    public ResponseEntity<ExceptionDetails> handlePageException(PageException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, exception);
    }

    @ExceptionHandler(AccessException.class)
    public ResponseEntity<ExceptionDetails> handleAccessException(AccessException exception) {
        return createResponseEntity(HttpStatus.UNAUTHORIZED, exception, CustomErrorCode.GENERAL);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDetails> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        return createResponseEntity(HttpStatus.METHOD_NOT_ALLOWED, LocalizationExceptionMessageValues.NO_METHOD.getMessage(),
                CustomErrorCode.GENERAL);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDetails> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, LocalizationExceptionMessageValues.BAD_REQUEST.getMessage(),
                CustomErrorCode.GENERAL);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionDetails> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException exception) {
        return createResponseEntity(HttpStatus.BAD_REQUEST, LocalizationExceptionMessageValues.WRONG_URL.getMessage(),
                CustomErrorCode.GENERAL);
    }

    private String getErrorResponse(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes())).getRequest();
        Locale locale = request.getLocale();
        String bundleMessage = resourceBundle.getMessage(key, new Object[] {}, locale);
        return bundleMessage;
    }

    private ResponseEntity createResponseEntity(HttpStatus status, CustomErrorExeption exception,
            String exceptionMessage) {
        String message = getErrorResponse(exceptionMessage);
        String errorCode = status.value() + exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    private ResponseEntity createResponseEntity(HttpStatus status, CustomErrorExeption exception) {
        String message = getErrorResponse(exception.getMessage());
        String errorCode = status.value() + exception.getCode();
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    private ResponseEntity createResponseEntity(HttpStatus status, Exception exception,
            CustomErrorCode customErrorCode) {
        String message = getErrorResponse(exception.getMessage());
        String exceptionCode = customErrorCode.getCode();
        String errorCode = status.value() + exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

    private ResponseEntity createResponseEntity(HttpStatus status, String exceptionMessage,
            CustomErrorCode customErrorCode) {
        String exceptionCode = customErrorCode.getCode();
        String message = getErrorResponse(exceptionMessage);
        String errorCode = status.value() + exceptionCode;
        ExceptionDetails data = new ExceptionDetails(LocalDateTime.now(), status.value(), message, errorCode);
        return new ResponseEntity<>(data, status);
    }

}
