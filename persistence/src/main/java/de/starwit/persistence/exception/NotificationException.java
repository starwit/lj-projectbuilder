package de.starwit.persistence.exception;

public class NotificationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String exceptionKey;
	private String exceptionMessage;
	private String[] parameters;
	
	public NotificationException(String exceptionKey, String exceptionMessage, String... parameters) {
		super("Error during app setup or generation.");
		this.exceptionKey = exceptionKey;
		this.exceptionMessage = exceptionMessage;
		this.parameters = parameters;
	}

	public String getExceptionKey() {
		return exceptionKey;
	}

	public void setExceptionKey(String exceptionKey) {
		this.exceptionKey = exceptionKey;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public String[] getParameters() {
		return this.parameters;
	}
}
