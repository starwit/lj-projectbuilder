package de.starwit.ljprojectbuilder.generator.frontend;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class FrontendModule extends AbstractModule {
	
	public FrontendModule(GeneratorDto setupBean) {
		super(setupBean);
		getPaths().setSource(Constants.SOURCE_FRONTEND_PATH);
		this.setModuleName("webclient");
		
		getTemplates().add(configureTemplateDef("all"));
		getTemplates().add(configureTemplateDef("single"));
		getTemplates().add(configureTemplateDef("ctrl"));
		getTemplates().add(configureTemplateDef("routes"));
		
		TemplateDef frontendConnectorT = new TemplateDef();
		frontendConnectorT.setSuffix(".connector.factory.js");
		frontendConnectorT.setTargetPath(getSrcDir() + Constants.FILE_SEP  + "shared" + Constants.FILE_SEP + "restfacade"  + Constants.FILE_SEP);
		frontendConnectorT.setTemplateFile("webclient" + Constants.FILE_SEP + "ctrl.ftl");
		frontendConnectorT.setCreateDomainDir(false);
		frontendConnectorT.setLowerCase(true);
		getTemplates().add(frontendConnectorT);
	}
	
	private TemplateDef configureTemplateDef(String name) {
		String targetPath = getSrcDir() + Constants.FILE_SEP  + "viewcomponents"  + Constants.FILE_SEP;
		TemplateDef frontendT = new TemplateDef();
		frontendT.setSuffix("." + name + ".js");
		frontendT.setTargetPath(targetPath);
		frontendT.setTemplateFile("webclient" + Constants.FILE_SEP + name + ".ftl");
		frontendT.setCreateDomainDir(true);
		frontendT.setLowerCase(true);
		return frontendT;
	}
}
