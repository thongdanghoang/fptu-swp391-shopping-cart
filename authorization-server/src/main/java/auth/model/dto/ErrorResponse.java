package auth.model.dto;

public record ErrorResponse(
        String error,
        String message,
        int status,
        String path,
        String timestamp) {
}
