package com.rw.library.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    public RestResponseEntityExceptionHandler() {
        super();
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + ": " + violation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.LENGTH_REQUIRED, ex.getLocalizedMessage(), errors);
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }


    @ExceptionHandler({ DataIntegrityViolationException.class })
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage(), ex.getRootCause().toString());
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ TransactionSystemException.class })
    public ResponseEntity<Object> handleTransactionSystemException(
            TransactionSystemException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.LENGTH_REQUIRED, ex.getLocalizedMessage(), ex.getOriginalException().getCause().getLocalizedMessage());
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ RollbackException.class })
    public ResponseEntity<Object> handleRollbackException(
            RollbackException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.LENGTH_REQUIRED, ex.getLocalizedMessage(), ex.getCause().getLocalizedMessage());
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ EmptyResultDataAccessException.class })
    public ResponseEntity<Object> handleEmptyResultDataAccess(
            EmptyResultDataAccessException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Empty result");
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ DomainObjectNotFoundException.class })
    public ResponseEntity<Object> handleBookNotFound(
            DomainObjectNotFoundException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), "Domain object not found");
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ EntityConstraintViolationException.class })
    public ResponseEntity<Object> handleEntityConstraintViolation(
            EntityConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.LENGTH_REQUIRED, ex.getLocalizedMessage(), "Improper parameter length");
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.BAD_REQUEST, "JSON parse error", ex.getMostSpecificCause().getMessage());
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String error = ex.getParameterName() + " parameter is missing";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, WebRequest request) {
        String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        ex.getSupportedHttpMethods().forEach(t -> builder.append(t).append(" "));
        ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
                ex.getLocalizedMessage(), builder.toString());
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ApiError apiError = new ApiError(HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage(), "error occurred");
        LOGGER.error("Status: " + apiError.getStatus() + "; " + apiError.getMessage() + "; " + apiError.getErrors() + "; " + ex.fillInStackTrace());
        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}