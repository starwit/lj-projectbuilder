package de.starwit.ljprojectbuilder.generator.rest;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class RestModule extends AbstractModule {

	public RestModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("rest");
		
		TemplateDef restT = new TemplateDef();
		restT.setSuffix("Rest.java");
		restT.setTargetPath(getSrcDir() + Constants.FILE_SEP  + "api" + Constants.FILE_SEP + "rest" + Constants.FILE_SEP);
		restT.setTemplateFile("rest" + Constants.FILE_SEP + "rest.ftl");
		restT.setUpperCaseFirst(true);
		getTemplates().add(restT);
	}
	
	@Override
	public String getSrcDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getSource()
				+ getSetupBean().getProject().getPackagePrefix()
				+ Constants.FILE_SEP + getSetupBean().getProject().getTitle();
	}
}
