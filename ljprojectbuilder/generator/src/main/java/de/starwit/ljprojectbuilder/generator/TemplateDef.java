package de.starwit.ljprojectbuilder.generator;

import java.io.File;
import java.io.IOException;

import de.starwit.ljprojectbuilder.config.Constants;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;
import logic.generators.GeneratorConfig;

public class TemplateDef {
	
	private String suffix = ".java";
	private String templateFile = "";
	private String targetPath = "";
	private boolean createDomainDir = false;
	private boolean upperCaseFirst = false;
	private boolean lowerCase = false;

	public String getTargetFileUrl(String domainname) {
		String checkedDir = "";
		if (createDomainDir) {
			checkedDir = checkOrCreateDir(domainname.toLowerCase()) ;
		}
		
		if (upperCaseFirst) {
			domainname = Constants.upperCaseFirst(domainname);
		} else if (lowerCase) {
			domainname = domainname.toLowerCase();
		}
		if (checkedDir != null) {
			return targetPath + Constants.FILE_SEP + domainname + suffix ;
		}
		return null;
	}

	private String checkOrCreateDir(String domainDir) {
		File checkedDir = new File(targetPath + domainDir);
		boolean success = true;
		if (!checkedDir.exists()) {
			success = checkedDir.mkdirs();
		}
		if (success) {
			return checkedDir.getPath();
		}
		return null;
	}
	
	public Template getTemplate()
			throws IOException, TemplateNotFoundException, MalformedTemplateNameException, ParseException {
		@SuppressWarnings("deprecation")
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(Constants.TEMPLATE_PATH));
		Template template = cfg.getTemplate(templateFile);
		return template;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getTemplateFile() {
		return templateFile;
	}

	public void setTemplateFile(String templateFile) {
		this.templateFile = templateFile;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public boolean isCreateDomainDir() {
		return createDomainDir;
	}

	public void setCreateDomainDir(boolean createDomainDir) {
		this.createDomainDir = createDomainDir;
	}

	public boolean isUpperCaseFirst() {
		return upperCaseFirst;
	}

	public void setUpperCaseFirst(boolean upperCaseFirst) {
		this.upperCaseFirst = upperCaseFirst;
	}

	public boolean isLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}
}
