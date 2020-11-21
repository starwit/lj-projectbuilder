package de.starwit.persistence.exception;

public class ImportException extends Exception {
	
	public enum MessageType {
		
		JSON_PARSING("Import json data could not be parsed. Please check your JSON file."),
		JSON_MAPPING("JSON could not be mapped to an object."),
		READ_FILE("Error reading the import file."),
		READ_CONFIG("Error reading the configuration file for import with path: ");
		
		
		private String message;
		
		private MessageType(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
		
	}

	private static final long serialVersionUID = 1L;
	
	private MessageType messageType;
	private Throwable throwable;
	private String[] params;
	
	public ImportException(Throwable t, MessageType messageType, String... params) {
		this.messageType = messageType;
		this.throwable = t;
		this.params = params;
	}
	
	public final Throwable getThrowable() {
		return throwable;
	}

	@Override
	public String getMessage() {
		String paramString = "";
		
		for (String param : params) {
			paramString = paramString + param;
		}
		return messageType.getMessage() + paramString +  super.getMessage();
	}

}
