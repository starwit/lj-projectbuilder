package de.starwit.generator.config;

public class Constants {

    private Constants() {
    };

    /** file separator */
    public static final String FILE_SEP = System.getProperty("file.separator");
    public static final String TMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String LJ_PREFIX = "LJ_";
    public static final String TEMPLATE_DIR = "generator-templates";
    public static final String TEMPLATE_PATH_PREFIX = "${apphome}/${app.targetPath}/generator-templates/";
    public static final String TARGET_PATH_PREFIX = "${apphome}/${app.targetPath}/${app.baseName}/";
    public static final String DEFAULT_BRANCH = "main";
    public static final String TEMPLATE_CONFIG = "template-config.json";
    public static final String APP_HOME = "${apphome}";
    /** deletion of temp files older than 1 minute **/
    public static final long MILLISECONDS_UNTIL_DELETION = 1 * 60 * 1000;
    public static final String GIT_URL_REGEX = "^(?:git|ssh|https?|git@[-w.]+):(//)?([a-zA-Z0-9./_-]{2,100})(.git)$";
    public static final String GIT_BRANCH_REGEX = "^([a-zA-Z0-9/_-]{2,100})$";
    public static final String GIT_USER_REGEX = "^[a-zA-Z0-9!@#$%^&()*./_-]{1,100}$";
    public static final String GIT_PASSWORD_REGEX = "^[a-zA-Z0-9!@#$%^&()*./_-]{6,100}$";
}
