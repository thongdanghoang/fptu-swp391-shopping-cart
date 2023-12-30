package com.thomasdang.as.config;

import com.thomasdang.as.model.dto.ErrorResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    private static final String ERROR_MESSAGE_TEMPLATE = "An unexpected application error occurred: %s";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        logger.error(ERROR_MESSAGE_TEMPLATE + ex.getMessage(), ex);

        // generally recommended to use @ExceptionHandler to handle runtime exceptions (unchecked exceptions)
        // rather than checked exceptions
        ErrorResponse er = new ErrorResponse();
        er.setTimestamp(LocalDateTime.now());
        er.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        er.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        er.setMessage(ERROR_MESSAGE_TEMPLATE + ex.getMessage());

        return new ResponseEntity<>(er, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException hmnrx) {

        logger.error(ERROR_MESSAGE_TEMPLATE + hmnrx.getMessage(), hmnrx);

        ErrorResponse errRes = new ErrorResponse();
        errRes.setTimestamp(LocalDateTime.now());
        errRes.setStatus(HttpStatus.BAD_REQUEST.value());
        errRes.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errRes.setMessage("Invalid JSON format: " + hmnrx.getMessage());

        return new ResponseEntity<>(errRes, HttpStatus.BAD_REQUEST);
    }
}
