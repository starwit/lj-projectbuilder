package de.starwit.ljprojectbuilder.dto;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilder.entity.ProjectEntity;
/**
 * Contains all configuration for project setup and generator.
 * @author Anett Huebner
 *
 */
@XmlRootElement
public class GeneratorDto {
	
	private ProjectEntity project;
	
	/**
	 * Enables or disables the generation of the database access layer
	 */
	private boolean generateEntity = true;
	
	/**
	 * Enables or disables the generation of the service layer to generate CRUD-services
	 */
	private boolean generateService = true;
	
	/**
	 * Enables or disables the generation of the REST API
	 */
	private boolean generateRest = true;
	
	/**
	 * Enables or disables the generation of the frontend module
	 */
	private boolean generateFrontend = true;
	
	/********************* GETTER, SETTER **************************/
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
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
}
