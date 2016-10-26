package de.starwit.ljprojectbuilder.generator.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class EntityModule extends AbstractModule {
	
	public EntityModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("persistence");
		
		TemplateDef entityT = new TemplateDef();
		entityT.setSuffix("Entity.java");
		entityT.setTargetPath(getSrcDir() + "entity" + Constants.FILE_SEP);
		entityT.setTemplateFile("entity" + Constants.FILE_SEP + "entity.ftl");
		entityT.setUpperCaseFirst(true);
		getDomainTemplates().add(entityT);
		
		getAdditionalContentTemplates().add(new TemplateDef(getTestResourcesDir() + "META-INF", "persistence.xml", "entity" + Constants.FILE_SEP + "persistence.ftl"));

	}
	
	@Override
	public String getSrcDir() {
		return this.getModuleDir()
				+ getPaths().getSource() + Constants.FILE_SEP + "de" + Constants.FILE_SEP
				+ getSetupBean().getProject().getPackagePrefix().toLowerCase()
				+ Constants.FILE_SEP + getSetupBean().getProject().getTitle().toLowerCase()
				+ Constants.FILE_SEP;
	}
	
	public String getTestResourcesDir() {
		return this.getModuleDir()
			+ getPaths().getTestResources()  + Constants.FILE_SEP;
	}
	
	public List<String> getEntityClasses() {
		String entityDir = getSrcDir()  + "entity" + Constants.FILE_SEP;
		List<DomainEntity> domains = getSetupBean().getDomains();
		List<String> domainnames = new ArrayList<String>();
		List<String> selecteddomainnames = new ArrayList<String>();
		for (DomainEntity domain : domains) {
			if (domain.isSelected()) {
				selecteddomainnames.add(Constants.upperCaseFirst(domain.getName()) + "Entity");
			}
			domainnames.add(Constants.upperCaseFirst(domain.getName()) + "Entity");
		}
		
		File folder = new File(entityDir);
		File[] listOfFiles = folder.listFiles();
	
		if (listOfFiles == null) {
			return selecteddomainnames;
		}
		
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String value = file.getName().replace(".java", "");
				if (domainnames.equals( value)) {
					if (!selecteddomainnames.equals(value))
						selecteddomainnames.add(value);
				}
			}
		}
		return selecteddomainnames;
	}
}
