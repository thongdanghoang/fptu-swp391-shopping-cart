package auth.web;

import auth.model.dto.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public abstract class BaseController {
    private static final String ERROR_MESSAGE_TEMPLATE = "An unexpected con error occurred: ";

    //We implement a @ControllerAdvice globally but also ResponseStatusExceptions locally
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException rse) {

        ErrorResponse response = new ErrorResponse(
                rse.getReason(),
                ERROR_MESSAGE_TEMPLATE + rse.getMessage(),
                rse.getStatusCode().value(),
                "/",
                LocalDateTime.now().toString()
        );

        return ResponseEntity.status(rse.getStatusCode().value()).body(response);
    }
}