package vn.id.thongdanghoang.n3tk.common.exceptions;

/**
 * Model of a server error to be sent to client in case error happens in the backend.
 * It contains http status and error detail.
 */
public record TechnicalErrorResponse(String correlationId, String message) implements ServerErrorResponse {

    @Override
    public ServerErrorType getErrorType() {
        return ServerErrorType.TECHNICAL;
    }
}