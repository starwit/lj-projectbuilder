package de.starwit.ljprojectbuilder.config;

public class Constants {

	private Constants() {
	};
	
	/** file separator   */
	public static final String FILE_SEP = System.getProperty("file.separator");
	
	/** source paths */
	public final static String SOURCE_PATH 	= "src" + FILE_SEP + "main" + FILE_SEP + "java" + FILE_SEP;
	public final static String RESOURCES_PATH 		= "src" + FILE_SEP + "main" + FILE_SEP + "resources" + FILE_SEP;
	public final static String TEST_PATH 	= "src" + FILE_SEP + "test" + FILE_SEP + "java" + FILE_SEP;
	public final static String TEST_RESOURCES_PATH 	= "src" + FILE_SEP + "test" + FILE_SEP + "resources" + FILE_SEP;
	public final static String SOURCE_FRONTEND_PATH = "src" + FILE_SEP + "main" + FILE_SEP + "webapp" + FILE_SEP;

	
	public final static String TEMPLATE_PATH = Constants.class.getClassLoader().getResource("").getPath().toString() + "../../../../generator/src/main/resources";
	
	public static String upperCaseFirst(String value) {

		// Convert String to char array.
		char[] array = value.toCharArray();
		// Modify first element in array.
		array[0] = Character.toUpperCase(array[0]);
		// Return string.
		return new String(array);
	  }
	

}
