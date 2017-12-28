package de.starwit.ljprojectbuilder.generator.rest;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class RestModule extends AbstractModule {
	
	private String restDir;
	private String restAppDir;

	
	public RestModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("rest");
		restDir = getSrcDir() + Constants.FILE_SEP  + "api" + Constants.FILE_SEP + "rest" + Constants.FILE_SEP;
		restAppDir = getSrcDir() + Constants.FILE_SEP  + "api" + Constants.FILE_SEP + "restapp" + Constants.FILE_SEP;
		
		TemplateDef restT = new TemplateDef();
		restT.setSuffix("Rest.java");
		restT.setTargetPath(restDir);
		restT.setTemplateFile("rest" + Constants.FILE_SEP + "rest.ftl");
		restT.setUpperCaseFirst(true);
		getDomainTemplates().add(restT);

		TemplateDef restAppT = new TemplateDef(restAppDir, "RestfulApplication.java", "rest" + Constants.FILE_SEP + "restfulApplication.ftl");
		getGlobalTemplates().add(restAppT);
	}
	@Override
	public String getSrcDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getSource() + Constants.FILE_SEP + "de" + Constants.FILE_SEP
				+ getSetupBean().getProject().getPackagePrefix().toLowerCase()
				+ Constants.FILE_SEP + getSetupBean().getProject().getTitle().toLowerCase();
	}
}
