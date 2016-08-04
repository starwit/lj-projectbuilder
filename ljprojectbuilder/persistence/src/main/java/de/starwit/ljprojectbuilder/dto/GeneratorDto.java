package de.starwit.ljprojectbuilder.dto;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilder.entity.DomainEntity;

@XmlRootElement
public class GeneratorDto {

	private String projectName;
	
	private String projectPath;
	
	private String templatePath;
	
	private String domainName;
	
	private boolean generateEntity;
	
	private boolean generateService;
	
	private boolean generateRest;
	
	private boolean generateFrontend;
	
	private boolean generateTests;
	
	private List<DomainEntity> domains = new ArrayList<DomainEntity>();

	
	/********************* GETTER, SETTER **************************/
	
	public GeneratorDto() {
		this.projectName = "lirejarp";
		this.projectPath = Paths.get("").toAbsolutePath().getParent().toString();
		this.templatePath = Paths.get("").toAbsolutePath().getParent().toString() + "/swingclient/src/main/resources";
	}
	
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectPath() {
		return projectPath;
	}

	public void setProjectPath(String projectPath) {
		this.projectPath = projectPath;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public boolean isGenerateEntity() {
		return generateEntity;
	}

	public void setGenerateEntity(boolean generateEntity) {
		this.generateEntity = generateEntity;
	}

	public boolean isGenerateService() {
		return generateService;
	}

	public void setGenerateService(boolean generateService) {
		this.generateService = generateService;
	}

	public boolean isGenerateRest() {
		return generateRest;
	}

	public void setGenerateRest(boolean generateRest) {
		this.generateRest = generateRest;
	}

	public boolean isGenerateFrontend() {
		return generateFrontend;
	}

	public void setGenerateFrontend(boolean generateFrontend) {
		this.generateFrontend = generateFrontend;
	}

	public boolean isGenerateTests() {
		return generateTests;
	}

	public void setGenerateTests(boolean generateTests) {
		this.generateTests = generateTests;
	}

	public List<DomainEntity> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainEntity> domains) {
		this.domains = domains;
	}
}
