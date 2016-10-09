package de.starwit.ljprojectbuilder.generator.entity;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class EntityModule extends AbstractModule {
	
	public EntityModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("persistence");
		
		TemplateDef entityT = new TemplateDef();
		entityT.setSuffix("Entity.java");
		entityT.setTargetPath(getSrcDir() + Constants.FILE_SEP  + "entity" + Constants.FILE_SEP);
		entityT.setTemplateFile("entity" + Constants.FILE_SEP + "entity.ftl");
		entityT.setUpperCaseFirst(true);
		getDomainTemplates().add(entityT);
	}
	
	@Override
	public String getSrcDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getSource()
				+ getSetupBean().getProject().getPackagePrefix()
				+ Constants.FILE_SEP + getSetupBean().getProject().getTitle();
	}
}
