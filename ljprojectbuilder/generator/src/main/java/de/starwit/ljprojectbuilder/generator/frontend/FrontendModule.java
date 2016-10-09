package de.starwit.ljprojectbuilder.generator.frontend;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class FrontendModule extends AbstractModule {
	
	public FrontendModule(GeneratorDto setupBean) {
		super(setupBean);
		getPaths().setSource(Constants.SOURCE_FRONTEND_PATH);
		this.setModuleName("webclient");
		
		getDomainTemplates().add(configureTemplateDef("all"));
		getDomainTemplates().add(configureTemplateDef("single"));
		getDomainTemplates().add(configureTemplateDef("ctrl"));
		getDomainTemplates().add(configureTemplateDef("routes"));
		
		TemplateDef frontendConnectorT = new TemplateDef();
		frontendConnectorT.setSuffix(".connector.factory.js");
		frontendConnectorT.setTargetPath(getSrcDir() + Constants.FILE_SEP  + "shared" + Constants.FILE_SEP + "restfacade"  + Constants.FILE_SEP);
		frontendConnectorT.setTemplateFile("webclient" + Constants.FILE_SEP + "ctrl.ftl");
		frontendConnectorT.setCreateDomainDir(false);
		frontendConnectorT.setLowerCase(true);
		getDomainTemplates().add(frontendConnectorT);

		getGlobalTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "webclient" + Constants.FILE_SEP + "scripts.ftl", "index.html"));
		getGlobalTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "webclient" + Constants.FILE_SEP + "app.module.ftl", "app.module.js"));
		getGlobalTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP, "webclient" + Constants.FILE_SEP + "menu.ftl", "menu.html"));
		getGlobalTemplates().add(new TemplateDef(getSrcDir() + Constants.FILE_SEP + "localization" + Constants.FILE_SEP, "webclient" + Constants.FILE_SEP + "translation.ftl", "translations-de-DE.json"));
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
	
	public List<String> getDomainnames() {
		List<DomainEntity> domains = getSetupBean().getDomains();
		List<String> domainnames = new ArrayList<String>();
		for (DomainEntity domain : domains) {
			domainnames.add(domain.getName().toLowerCase());
		}
		
		String targetPath = getSrcDir() + Constants.FILE_SEP  + "viewcomponents"  + Constants.FILE_SEP;
		File folder = new File(targetPath);
		File[] listOfFiles = folder.listFiles();
		int l = listOfFiles.length;
		List<String> domainnamesInFiles = new ArrayList<String>(l);
		for (File file : listOfFiles) {
			if (file.isDirectory()) {
				domainnamesInFiles.add(file.getName());
			}
		}
		
		domainnames.retainAll(domainnamesInFiles);
		return domainnames;
	}
}
