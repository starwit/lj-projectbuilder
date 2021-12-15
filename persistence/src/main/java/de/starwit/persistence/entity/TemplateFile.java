package de.starwit.persistence.entity;

import java.io.File;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
public class TemplateFile extends AbstractEntity<Long> {

	@Length(max = 100)
	@NotBlank
	private String fileName;

	@NotBlank
	private String templatePath;

	@XmlTransient
	private String concreteTemplatePath = "";

	@XmlTransient
	@NotBlank
	private String targetPath;

	@Transient
	private String concreteTargetPath = "";

	private boolean createDomainDir = false;

	private boolean upperCaseFirst = false;

	private boolean lowerCase = false;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TemplateType type = TemplateType.DOMAIN;

	@NotBlank
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	private String category;

	public String getFileNameSuffix() {
		return fileName;
	}

	public void setFileNameSuffix(String fileName) {
		this.fileName = fileName;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
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

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@XmlTransient
	@Transient
	public String getTargetFileUrl(String domainname) {
		String checkedDir = concreteTargetPath;
		if (createDomainDir) {
			checkedDir = checkOrCreateDir(domainname.toLowerCase());
		}

		if (upperCaseFirst) {
			domainname = TemplateFile.upperCaseFirst(domainname);
		} else if (lowerCase) {
			domainname = domainname.toLowerCase();
		}
		if (checkedDir != null) {
			String targetUrl = checkedDir + domainname + fileName;
			return targetUrl;
		}
		return null;
	}

	private String checkOrCreateDir(String domainDir) {
		File checkedDir = new File(concreteTargetPath + domainDir);
		boolean success = true;
		if (!checkedDir.exists()) {
			success = checkedDir.mkdirs();
		}
		if (success) {
			return checkedDir.getPath() + System.getProperty("file.separator");
		}
		return null;
	}

	public static String upperCaseFirst(String value) {
		// Convert String to char array.
		char[] array = value.toCharArray();
		// Modify first element in array.
		array[0] = Character.toUpperCase(array[0]);
		// Return string.
		return new String(array);
	}

	@XmlTransient
	public String getConcreteTargetPath() {
		return concreteTargetPath;
	}

	public void setConcreteTargetPath(String concreteTargetPath) {
		this.concreteTargetPath = concreteTargetPath;
	}

	@XmlTransient
	public String getConcreteTemplatePath() {
		return concreteTemplatePath;
	}

	public void setConcreteTemplatePath(String concreteTemplatePath) {
		this.concreteTemplatePath = concreteTemplatePath;
	}
}
