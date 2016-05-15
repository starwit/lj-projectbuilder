package de.starwit.ljprojectbuilderp.exception;

public class IllegalIdException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String className;

	public IllegalIdException(String className) {
		this.className = className;
	}
	
	@Override
	public String getMessage() {
		String notFound = "Illegal ID for Entity " + className + ". ";
		return notFound;
	}

}
