package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name="PROJECT")
public class ProjectEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	@Size(max=100)
	private String templateLocation;
	
	@NotBlank
	@Size(max=100)
	private String templateTitle;
	
	@NotBlank
	@Size(max=100)
	private String templatePackagePrefix;
	
	@NotBlank
	@Size(max=100)
	private String title;
	
	@NotBlank
	@Max(value=100)
	private String packagePrefix;
	
	@NotBlank
	@Size(max=100)
	private String targetPath;
	
	@NotBlank
	private String description;
	
	@Column(name="TEMPLATE_TITLE", nullable=false, length=100)
	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	@Column(name="TEMPLATEPREFIX", nullable=false, length=100)
	public String getTemplatePackagePrefix() {
		return templatePackagePrefix;
	}

	public void setTemplatePackagePrefix(String templatePackagePrefix) {
		this.templatePackagePrefix = templatePackagePrefix;
	}
	
	@Column(name="TITLE", nullable = false, length=100)
	public String getTitle() {
		return title;
	}
	
		public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="PACKAGEPREFIX", nullable = false, length=100)
	public String getPackagePrefix() {
		return packagePrefix;
	}
	
		public void setPackagePrefix(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}

	@Column(name="DESCRIPTION", nullable = false, length=100)
	public String getDescription() {
		return description;
	}
	
		public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="TEMPLATELOCATION", nullable = false, length=100)
	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
	
	@Column(name="TARGETPATH", nullable = false, length=100)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}
}