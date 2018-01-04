package de.starwit.generator.dto;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.ljprojectbuilder.entity.DomainEntity;
import de.starwit.ljprojectbuilder.entity.ProjectEntity;
/**
 * Contains all configuration for project setup and generator.
 * @author Anett Huebner
 *
 */
@XmlRootElement
public class GeneratorDto {
	
	private ProjectEntity project;
	
	private Set<DomainEntity> selectedDomains;
	
	/********************* GETTER, SETTER **************************/
	
	public ProjectEntity getProject() {
		return project;
	}

	public void setProject(ProjectEntity project) {
		this.project = project;
	}

	public Set<DomainEntity> getSelectedDomains() {
		return selectedDomains;
	}

	public void setSelectedDomains(Set<DomainEntity> selectedDomains) {
		this.selectedDomains = selectedDomains;
	}
}
