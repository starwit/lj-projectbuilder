package de.starwit.generator.config;

public class Constants {

	private Constants() {
	};
	
	/** file separator   */
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	public static final String LJ_PREFIX = "LJ_";
	public static final String TEMPLATE_DIR = "generator-templates";
	public static final String DEFAULT_BRANCH = "master";
}
