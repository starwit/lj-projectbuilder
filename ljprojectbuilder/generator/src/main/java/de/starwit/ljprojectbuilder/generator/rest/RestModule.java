package de.starwit.ljprojectbuilder.generator.rest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.starwit.ljprojectbuilder.config.Constants;
import de.starwit.ljprojectbuilder.dto.GeneratorDto;
import de.starwit.ljprojectbuilder.generator.AbstractModule;
import de.starwit.ljprojectbuilder.generator.TemplateDef;

public class RestModule extends AbstractModule {
	
	private String restDir = getSrcDir() + Constants.FILE_SEP  + "api" + Constants.FILE_SEP + "rest" + Constants.FILE_SEP;
	private String restAppDir = getSrcDir() + Constants.FILE_SEP  + "api" + Constants.FILE_SEP + "restapp" + Constants.FILE_SEP;

	
	public RestModule(GeneratorDto setupBean) {
		super(setupBean);
		this.setModuleName("rest");
		
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
				+ Constants.FILE_SEP + getPaths().getSource()
				+ getSetupBean().getProject().getPackagePrefix()
				+ Constants.FILE_SEP + getSetupBean().getProject().getTitle();
	}
	
	public List<String> getRestClassnames() {
		File folder = new File(restDir);
		File[] listOfFiles = folder.listFiles();
		int l = listOfFiles.length;

		List<String> classnames = new ArrayList<String>(l);
		for (File file : listOfFiles) {
			if (file.isFile()) {
				String value = file.getName().replace(".java", "");
				classnames.add(value);
			}
		}
		return classnames;
	}
}
