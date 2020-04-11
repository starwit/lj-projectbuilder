package de.starwit.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.entity.AttributeEntity;

@XmlRootElement
@Entity
@Table(name = "DOMAIN")
public class DomainDto extends AbstractEntity<Long> {

	@NotNull
	private Long projectId;

	// domain attributes

	@NotBlank
	private String name;

	private String description;

	private Set<AttributeEntity> attributes;

	private boolean selected = true;

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<AttributeEntity> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<AttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
}