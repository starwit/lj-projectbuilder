package de.starwit.ljprojectbuilder.generator;

import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;

public abstract class AbstractModule {
	
	private String moduleName;
	
	private SrcPathDef  paths = new SrcPathDef();
	
	private List<TemplateDef> domainTemplates = new ArrayList<>();
	private List<TemplateDef> globalTemplates = new ArrayList<>();
	private List<TemplateDef> additionalContentTemplates = new ArrayList<>();
	
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
		return 
				Constants.TMP_DIR 
				+ Constants.FILE_SEP + 	setupBean.getProject().getTargetPath() 
				+ Constants.FILE_SEP + setupBean.getProject().getTitle() 
				+ Constants.FILE_SEP + getModuleName() 
				+ Constants.FILE_SEP;
	}
	
	public String getSrcDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getSource();
	}
	
	public String getTestDir() {
		return this.getModuleDir()
				+ Constants.FILE_SEP + getPaths().getTest();
	}

	public List<TemplateDef> getDomainTemplates() {
		return domainTemplates;
	}

	public void setDomainTemplates(List<TemplateDef> domainTemplates) {
		this.domainTemplates = domainTemplates;
	}

	public GeneratorDto getSetupBean() {
		return setupBean;
	}

	public void setSetupBean(GeneratorDto setupBean) {
		this.setupBean = setupBean;
	}

	public List<TemplateDef> getGlobalTemplates() {
		return globalTemplates;
	}

	public void setGlobalTemplates(List<TemplateDef> globalTemplates) {
		this.globalTemplates = globalTemplates;
	}

	public List<TemplateDef> getAdditionalContentTemplates() {
		return additionalContentTemplates;
	}

	public void setAdditionalContentTemplates(List<TemplateDef> additionalContentTemplates) {
		this.additionalContentTemplates = additionalContentTemplates;
	}
	
}
