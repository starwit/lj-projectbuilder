package de.starwit.persistence.exception;

public class NotificationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String exceptionKey;
	private String exceptionMessage;
	
	public NotificationException(String exceptionKey, String exceptionMessage) {
		super("Error during app setup or generation.");
		this.exceptionKey = exceptionKey;
		this.exceptionMessage = exceptionMessage;
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
}
