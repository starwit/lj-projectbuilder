package de.starwit.generator.exeptions;

import de.starwit.ljprojectbuilder.response.ResponseMetadata;

public class ProjectSetupException extends Exception {

	private static final long serialVersionUID = 1L;
	private ResponseMetadata responseMetadata;

	public ProjectSetupException(ResponseMetadata responseMetadata) {
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
