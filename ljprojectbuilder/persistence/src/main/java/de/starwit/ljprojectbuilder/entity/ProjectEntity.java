package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name="PROJECT")
public class ProjectEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	
	@NotBlank
	private String templateLocation;
	
	@NotBlank
	private String title;
	
	@NotBlank
	private String packagePrefix;
	
	@NotBlank
	private String description;
	
	

	@Column(name="TITLE", nullable = false)
	public String getTitle() {
		return title;
	}
	
		public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="PACKAGEPREFIX", nullable = false)
	public String getPackagePrefix() {
		return packagePrefix;
	}
	
		public void setPackagePrefix(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}

	@Column(name="DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}
	
		public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="TEMPLATELOCATION", nullable = false)
	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
}