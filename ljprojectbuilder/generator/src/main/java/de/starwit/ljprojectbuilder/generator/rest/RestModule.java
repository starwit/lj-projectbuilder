package de.starwit.ljprojectbuilder.generator.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
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
	
	public List<String> getRestClassnames() {
		List<DomainEntity> domains = getSetupBean().getDomains();
		List<String> domainnames = new ArrayList<String>();
		List<String> selecteddomainnames = new ArrayList<String>();
		for (DomainEntity domain : domains) {
			if (domain.isSelected()) {
				selecteddomainnames.add(Constants.upperCaseFirst(domain.getName()) + "Rest");
			}
			domainnames.add(Constants.upperCaseFirst(domain.getName()) + "Rest");
		}
		
		File folder = new File(restDir);
		File[] listOfFiles = folder.listFiles();
	
		if (listOfFiles == null) {
			return selecteddomainnames;
		}
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String value = file.getName().replace(".java", "");
				if (domainnames.contains(value)) {
					if (!selecteddomainnames.contains(value))
						selecteddomainnames.add(value);
				}
			}
		}
		return selecteddomainnames;
	}
}
