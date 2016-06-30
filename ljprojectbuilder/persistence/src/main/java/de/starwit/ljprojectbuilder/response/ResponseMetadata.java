package de.starwit.ljprojectbuilder.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilder.validation.ValidationError;

@XmlRootElement
public class ResponseMetadata {

	private ResponseCode responseCode;
	
	private String message = "Es sind keine Fehler aufgetreten.";
	
	private List<ValidationError> validationErrors;
	
	public List<ValidationError> getValidationErrors() {
		return validationErrors;
	}

	public void setValidationErrors(List<ValidationError> validationErrors) {
		this.validationErrors = validationErrors;
	}

	public ResponseMetadata() {
	}

	public ResponseMetadata(ResponseCode responseCode, String message) {
		this.responseCode = responseCode;
		this.message = message;
	}
	
	public ResponseMetadata(ResponseCode responseCode, String message, List<ValidationError> validationErrors) {
		this(responseCode, message);
		this.validationErrors = validationErrors;
	}
	
	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}
	
	public ResponseCode getResponseCode() {
		return responseCode;
	}
	
	public String getMsgCode() {
		return this.responseCode.getMsgCode();
	}
	
	public void setMsgCode(String msgCode) {
		this.responseCode = ResponseCode.ERROR;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
