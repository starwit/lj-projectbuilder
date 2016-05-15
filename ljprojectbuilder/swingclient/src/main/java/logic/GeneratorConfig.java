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
	
	ALL_UI(".all.html", "webclient/all.ftl",  "viewcomponents"),
	MAINTAIN_UI(".single.html", "webclient/single.ftl",  "viewcomponents"),
	
	CONTROLLER_UI(".ctrl.js", "webclient/ctrl.ftl", "viewcomponents"),
	CONFIG_UI(".routes.js", "webclient/routes.ftl", "viewcomponents"),
	CONNECTOR_UI(".connector.factory.js", "webclient/connector.ftl", "shared/restfacade"),
	
	SCRIPT_BINDING("index.html", "webclient/scripts.ftl", ""),
	VIEWS_UI("app.module.js", "webclient/app.module.ftl", ""),
	MENU_UI("menu.html", "webclient/menu.ftl", ""),
	TRANSLATION_UI("translations-de-DE.json", "webclient/translation.ftl", "localization"),

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
