package de.starwit.persistence.exception;

import de.starwit.persistence.response.ResponseMetadata;

public class NotificationException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private ResponseMetadata responseMetadata;
	
	public NotificationException(ResponseMetadata responseMetadata) {
		super("Error during project setup or generation.");
		this.responseMetadata = responseMetadata;
	}

	public ResponseMetadata getResponseMetadata() {
		return responseMetadata;
	}

	public void setResponseMetadata(ResponseMetadata responseMetadata) {
		this.responseMetadata = responseMetadata;
	}
	

}
