package com.test.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.exception.BusinessValidationException;
import com.test.service.MessageService;
import com.test.util.ApiResponse;
import com.test.util.Constant;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.NoSuchElementException;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice()
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);
    static final String INTERNAL_SERVER_ERROR_MSG = "Something went wrong, Please try after sometime.";
    @Autowired
    protected MessageService messageService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(HttpStatus.BAD_REQUEST, error);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus badRequest, String errorMessage) {
        LOG.error("apiError " + errorMessage);
        return new ResponseEntity<>(new com.test.util.ApiResponse<>(objectMapper.createObjectNode(), errorMessage, Constant.FAIL), badRequest);
    }

    @ExceptionHandler({BusinessValidationException.class})
    protected ResponseEntity<Object> handleBusinessValidation(BusinessValidationException ex, HttpServletRequest request) {
        LOG.error("apiError " + getMessage(ex.getMessage(), ex.getParams()));
        return new ResponseEntity<>(new ApiResponse<>(null, getMessage(ex.getMessage(), ex.getParams()), Constant.FAIL), HttpStatus.OK);
    }


    @ExceptionHandler({InvalidParameterException.class})
    protected ResponseEntity<Object> handleInvalidParameter(InvalidParameterException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }


    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.NOT_FOUND, messageService.getMessage(ex.getMessage()));
    }


    @ExceptionHandler({DataIntegrityViolationException.class})
    protected ResponseEntity<Object> handleEndUserException(DataIntegrityViolationException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.OK, messageService.getMessage(ex.getMessage()));
    }

    @ExceptionHandler({SQLException.class})
    protected ResponseEntity<Object> handleSQLException(SQLException ex, HttpServletRequest request) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, messageService.getMessage(ex.getMessage()));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException be, HttpServletRequest request) {
        for (ConstraintViolation<?> exMessage : be.getConstraintViolations()) {
            return buildResponseEntity(HttpStatus.OK, messageService.getMessage(exMessage.getMessage()));
        }
        return buildResponseEntity(HttpStatus.OK, messageService.getMessage(be.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        return buildResponseEntity(HttpStatus.OK, getMessage(result.getFieldError().getDefaultMessage()));
    }

    private String getMessage(String key, Object[] params) {
        try {
            return messageService.getMessage(key, params);
        } catch (Exception e) {
            return key;
        }
    }

    private String getMessage(String key) {
        try {
            return messageService.getMessage(key);
        } catch (Exception e) {
            return key;
        }
    }
}
