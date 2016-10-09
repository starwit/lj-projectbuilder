package de.starwit.ljprojectbuilder.generator;

import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;

public abstract class AbstractModule {
	
	private String moduleName;
	
	private SrcPathDef  paths = new SrcPathDef();
	
	private List<TemplateDef> templates = new ArrayList<>();
	
	private GeneratorDto setupBean;
	

	public AbstractModule(GeneratorDto setupBean) {
		this.setupBean = setupBean;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public SrcPathDef getPaths() {
		return paths;
	}

	public void setPaths(SrcPathDef paths) {
		this.paths = paths;
	}
	
	public String getModuleDir() {
		return setupBean.getProject().getTargetPath() 
				+ Constants.FILE_SEP + setupBean.getProject().getTitle() 
				+ Constants.FILE_SEP + getModuleName() 
				+ Constants.FILE_SEP;
	}
	
	public String getSrcDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getSource();
	}

	public List<TemplateDef> getTemplates() {
		return templates;
	}

	public void setTemplates(List<TemplateDef> templates) {
		this.templates = templates;
	}

	public GeneratorDto getSetupBean() {
		return setupBean;
	}

	public void setSetupBean(GeneratorDto setupBean) {
		this.setupBean = setupBean;
	}
	
}
