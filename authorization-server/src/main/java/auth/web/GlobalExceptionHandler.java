package auth.web;

import auth.model.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.time.LocalDateTime;

//@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ERROR_MESSAGE_TEMPLATE = "An unexpected application error occurred: ";

    //    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {

        var errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ERROR_MESSAGE_TEMPLATE + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "/",
                LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpMessageNotReadableException exception) {

        var errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Invalid JSON format: " + exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                "/",
                LocalDateTime.now().toString()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

