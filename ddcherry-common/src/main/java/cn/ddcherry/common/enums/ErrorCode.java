package cn.ddcherry.common.enums;

/**
 * Basic error codes for unified responses.
 */
public enum ErrorCode {
    SUCCESS(0, "OK"),
    VALIDATION_ERROR(1001, "Validation failed"),
    SYSTEM_ERROR(2000, "System error"),
    BUSINESS_ERROR(2001, "Business rule violation"),
    UNAUTHORIZED(4001, "Not logged in or session expired"),
    FORBIDDEN(4003, "Access denied");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
