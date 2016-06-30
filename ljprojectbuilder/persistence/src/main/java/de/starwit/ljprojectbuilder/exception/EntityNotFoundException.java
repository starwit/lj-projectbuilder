package de.starwit.ljprojectbuilder.exception;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 4644643097889433029L;
	
	private Long id;
	private String className;

	public EntityNotFoundException(Long id, String className) {
		this.id = id;
		this.className = className;
	}
	
	@Override
	public String getMessage() {
		String notFound = "Entity " + className + " of id " + id + " could not be found. ";
		return notFound;
	}
}
