package de.starwit.generator.config;

public class Constants {

	private Constants() {
	};
	
	/** file separator   */
	public static final String FILE_SEP = System.getProperty("file.separator");
	public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
	public static final String LJ_PREFIX = "LJ_";
	public static final String TEMPLATE_DIR = "generator-templates";
	public static final String TEMPLATE_PATH_PREFIX = "${projecthome}/${project.targetPath}/generator-templates/";
	public static final String TARGET_PATH_PREFIX = "${projecthome}/${project.targetPath}/${project.title}/";
	public static final String DEFAULT_BRANCH = "main";
	public static final String TEMPLATE_CONFIG = "template-config.json";
	public static final String APP_HOME = "${projecthome}";
	/**deletion of temp files older than 1 minute **/
	public static final long MILLISECONDS_UNTIL_DELETION = 1 * 60 * 1000;
}
