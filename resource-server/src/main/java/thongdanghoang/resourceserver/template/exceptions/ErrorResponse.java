package thongdanghoang.resourceserver.template.exceptions;

import org.springframework.http.ProblemDetail;

import java.time.LocalDateTime;

@lombok.Getter
@lombok.Setter
public class ErrorResponse {
    private ProblemDetail problem;
    private LocalDateTime timestamp;

    public static ErrorResponse from(ProblemDetail problemDetail) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setProblem(problemDetail);
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }
}