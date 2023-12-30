package com.thomasdang.as.web;

import com.thomasdang.as.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public abstract class BaseController {
    //We implement a @ControllerAdvice globally but also ResponseStatusExceptions locally
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException rse) {
        HttpStatus httpStatus = rse.getStatus();
        String errorMessage = rse.getReason();

        ErrorResponse er = new ErrorResponse();
        er.setStatus(httpStatus.value());
        er.setError(httpStatus.getReasonPhrase());
        er.setMessage(errorMessage);
        er.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(httpStatus).body(er);
    }
}
