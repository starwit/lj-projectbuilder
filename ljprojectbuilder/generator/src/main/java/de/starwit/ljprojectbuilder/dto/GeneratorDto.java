package de.starwit.ljprojectbuilder.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;

@XmlRootElement
public class GeneratorDto {

	private ProjectEntity project;
	
	private String templatePath;
	
	private boolean generateEntity;
	
	private boolean generateService;
	
	private boolean generateRest;
	
	private boolean generateFrontend;
	
	private boolean generateTests;
	
	private List<DomainEntity> domains = new ArrayList<DomainEntity>();

	
	/********************* GETTER, SETTER **************************/
	
	public GeneratorDto() {
		this.templatePath = this.getClass().getClassLoader().getResource("").getPath().toString() + "../../../../generator/src/main/resources";
	}
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
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
