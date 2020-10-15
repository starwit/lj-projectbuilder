package de.starwit.exception;

public class AbstractException extends RuntimeException {

    private GoogleHttpStatus status;
    private String localizationKey;

    public AbstractException(GoogleHttpStatus status, String localizationKey) {
        this.status = status;
        this.localizationKey = localizationKey;
    }

    public GoogleHttpStatus getStatus() {
        return status;
    }

    public void setStatus(GoogleHttpStatus status) {
        this.status = status;
    }

    public String getLocalizationKey() {
        return localizationKey;
    }

    public void setLocalizationKey(String localizationKey) {
        this.localizationKey = localizationKey;
    }
}
