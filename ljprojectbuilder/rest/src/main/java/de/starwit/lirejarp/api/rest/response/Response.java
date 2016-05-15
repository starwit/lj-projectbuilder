package de.starwit.lirejarp.api.rest.response;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.lirejarp.api.rest.validation.ValidationError;

@XmlRootElement
public class Response<E> {
	
	private E result;
	
	private ResponseMetadata metadata;
	
	public Response() {
		//default constructor
	}
	
	public Response(E result) {
		this.result = result;
	}
	
//	@JsonIgnoreProperties({ "scanEvents", "packingPieces"})
	public E getResult() {
		return result;
	}

	public void setResult(E result) {
		this.result = result;
	}

	public ResponseMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ResponseMetadata metadata) {
		this.metadata = metadata;
	}	
	
	public void setMetadata(ResponseCode responseCode, String message) {
		if (this.metadata == null) {
			this.metadata = new ResponseMetadata(responseCode, message);
		} else {
			this.metadata.setResponseCode(responseCode);
			this.metadata.setMessage(message);
		}
	}	
	
	public void setMetadata(ResponseCode responseCode, String message, List<ValidationError> validationErrors) {
		if (this.metadata == null) {
			this.metadata = new ResponseMetadata(responseCode, message, validationErrors);
		} else {
			this.metadata.setResponseCode(responseCode);
			this.metadata.setMessage(message);
			this.metadata.setValidationErrors(validationErrors);
		}
	}	
	
}
