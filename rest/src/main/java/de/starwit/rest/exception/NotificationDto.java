package de.starwit.rest.exception;

public class NotificationDto {

    private String messageKey;
    private String message;

    public NotificationDto() {}

    public NotificationDto(String messageKey, String message) {
        this.messageKey = messageKey;
        this.message = message;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
