package de.starwit.exception;

public enum GoogleHttpStatus {


    INVALID_ARGUMENT(400, "INVALID_ARGUMENT"),
    FAILED_PRECONDITION(400, "FAILED_PRECONDITION"),
    OUT_OF_RANGE(400, "OUT_OF_RANGE"),
    BAD_REQUEST(400, "BAD_REQUEST"),
    UNAUTHENTICATED(401, "UNAUTHENTICATED"),
    PERMISSION_DENIED(403, "PERMISSION_DENIED"),
    NOT_FOUND(404, "NOT_FOUND"),
    ABORTED(409, "ABORTED"),
    ALREADY_EXISTS(409, "ALREADY_EXISTS"),
    RESOURCE_EXHAUSTED(429, "RESOURCE_EXHAUSTED"),
    CANCELLED(499, "CANCELLED"),
    DATA_LOSS(500, "DATA_LOSS"),
    UNKNOWN(500, "UNKNOWN"),
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    NOT_IMPLEMENTED(501, "NOT_IMPLEMENTED"),
    UNAVAILABLE(503, "UNAVAILABLE"),
    DEADLINE_EXCEEDED(504, "DEADLINE_EXCEEDED");


    private Integer value;
    private String reasonPhrase;


    GoogleHttpStatus(Integer value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }

    public void setReasonPhrase(String reasonPhrase) {
        this.reasonPhrase = reasonPhrase;
    }

}
