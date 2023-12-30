package thongdanghoang.resourceserver.shared;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import thongdanghoang.resourceserver.template.exceptions.ErrorResponse;

@RestControllerAdvice
public abstract class AbstractApplicationController {

    // We implement a @ControllerAdvice globally but also ResponseStatusExceptions locally
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException exception) {
        return ResponseEntity
                .status(exception.getStatusCode())
                .body(ErrorResponse.from(exception.getBody()));
    }

}
