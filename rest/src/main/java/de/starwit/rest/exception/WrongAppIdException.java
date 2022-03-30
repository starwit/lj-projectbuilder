package de.starwit.rest.exception;

public class WrongAppIdException extends RuntimeException {

    public WrongAppIdException(Long appId, String entityName) {
        super("Entity with name " + entityName + "is not assigned to app with id " + appId + ".");
    }
}
