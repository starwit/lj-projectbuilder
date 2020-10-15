package de.starwit.exception;

public class EntityNotFoundException extends AbstractException {
    public EntityNotFoundException() {
        super(GoogleHttpStatus.NOT_FOUND, "exception.notFound");
    }
}
