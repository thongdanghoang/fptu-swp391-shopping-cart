package auth.model.dto;

public record SuccessResponse(
        int status,
        String message,
        Object data
) {
}
