package logic;

/**
 * Configuration of the code generator.
 * @author anett
 *
 */
public enum GeneratorConfig {
	ENTITY("Entity.java", "entity/entity.ftl",  "entity"),
	
	SERVICE_INTERFACE("Service.java", "service/service.ftl", "ejb"),
	SERVICE_IMPL("ServiceImpl.java", "service/serviceImpl.ftl", "ejb/impl"),
	SERVICE_TEST("ServiceTest.java", "service/serviceTest.ftl", "ejb"),
	JUNITTESTDATA("Entity.json", "test/entityDataImport.ftl", "datasets"),

	REST("Rest.java", "rest/rest.ftl", "api/rest"),
	REST_APP("RestfulApplication.java", "rest/restfulApplication.ftl", "api/restapp"),
	
	ALL_UI("-all.html", "frontend/all.ftl",  "views"),
	MAINTAIN_UI("-single.html", "frontend/single.ftl",  "views"),
	
	CONTROLLER_UI("-ctrl.js", "frontend/ctrl.ftl", "views"),
	CONFIG_UI("-config.js", "frontend/config.ftl", "views"),
	CONNECTOR_UI("-connector-factory.js", "frontend/connector.ftl", "serviceconnector"),
	
	SCRIPT_BINDING("index.html", "frontend/scripts.ftl", ""),
	VIEWS_UI("app.config.js", "frontend/app.config.ftl", ""),
	MENU_UI("menu.html", "frontend/menu.ftl", ""),
	TRANSLATION_UI("translations-de-DE.json", "frontend/translation.ftl", "localization"),

	TESTDATA("Entity.json", "test/entityDataImport.ftl", "datasets");
	
	public String suffix;
	public String templateFile;
	public String targetPath;
	
	private GeneratorConfig(String suffix, String templateFile, String targetPath) {
		this.suffix = suffix;
		this.templateFile = templateFile;
		this.targetPath = targetPath;
		
	}

	/**
	 * @return The standard-appendix of the file for all domain objects.
	 */
	public String getSuffix() {
		return suffix;
	}

	/**
	 * @return Freemarker template file
	 */
	public String getTemplateFile() {
		return templateFile;
	}

	/**
	 * @return Path where the new file is generated to.
	 */
	public String getTargetPath() {
		return targetPath;
	}
}
