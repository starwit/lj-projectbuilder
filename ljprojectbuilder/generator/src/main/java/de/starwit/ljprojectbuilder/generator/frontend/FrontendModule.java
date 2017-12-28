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
		
		getDomainTemplates().add(configureTemplateDef("all", ".html"));
		getDomainTemplates().add(configureTemplateDef("single", ".html"));
		TemplateDef allCtrl = configureTemplateDef("AllCtrl", ".js");
		allCtrl.setSuffix("AllCtrl.js");
		getDomainTemplates().add(allCtrl);
		TemplateDef singleCtrl = configureTemplateDef("SingleCtrl", ".js");
		singleCtrl.setSuffix("SingleCtrl.js");
		getDomainTemplates().add(singleCtrl);
		getDomainTemplates().add(configureTemplateDef("routes", ".js"));
		getDomainTemplates().add(configureTemplateDef("module", ".js"));
		
		TemplateDef frontendConnectorT = new TemplateDef();
		frontendConnectorT.setSuffix(".connector.factory.js");
		frontendConnectorT.setTargetPath(getSrcDir() + Constants.FILE_SEP  + "shared" + Constants.FILE_SEP + "restfacade"  + Constants.FILE_SEP);
		frontendConnectorT.setTemplateFile("webclient" + Constants.FILE_SEP + "connector.ftl");
		frontendConnectorT.setCreateDomainDir(false);
		frontendConnectorT.setLowerCase(true);
		getDomainTemplates().add(frontendConnectorT);

		getAdditionalContentTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "index.html", "webclient" + Constants.FILE_SEP + "scripts.ftl"));
		getAdditionalContentTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "app.module.js", "webclient" + Constants.FILE_SEP + "app.module.ftl"));
		getAdditionalContentTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "menu.html", "webclient" + Constants.FILE_SEP + "menu.ftl"));
		getAdditionalContentTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP + "localization" + Constants.FILE_SEP, "translations-de-DE.json", "webclient" + Constants.FILE_SEP + "translations-de-DE.ftl"));
		getAdditionalContentTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP + "localization" + Constants.FILE_SEP, "translations-en-US.json", "webclient" + Constants.FILE_SEP + "translations-en-US.ftl"));
	}

	private TemplateDef configureTemplateDef(String name, String fileext) {
		String targetPath = getSrcDir() + Constants.FILE_SEP  + "viewcomponents"  + Constants.FILE_SEP;
		TemplateDef frontendT = new TemplateDef();
		frontendT.setSuffix("." + name + fileext);
		frontendT.setTargetPath(targetPath);
		frontendT.setTemplateFile("webclient" + Constants.FILE_SEP + name + ".ftl");
		frontendT.setCreateDomainDir(true);
		frontendT.setLowerCase(true);
		return frontendT;
	}
	
}
