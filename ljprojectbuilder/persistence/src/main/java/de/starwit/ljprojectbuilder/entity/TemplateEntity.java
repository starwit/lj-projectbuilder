package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name="TEMPLATE")
public class TemplateEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max=100)
	private String templateLocation = "https://github.com/witchpou/lirejarp.git";
	
	@NotNull
	@Size(max=100)
	private String templateTitle = "lirejarp";
	
	@NotNull
	@Size(max=100)
	private String templatePackagePrefix = "starwit";
	
	@Size(max=100)
	private String branch = "master";
	
	@Column(name="TITLE", nullable=false, length=100)
	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	@Column(name="PREFIX", nullable=false, length=100)
	public String getTemplatePackagePrefix() {
		return templatePackagePrefix;
	}

	public void setTemplatePackagePrefix(String templatePackagePrefix) {
		this.templatePackagePrefix = templatePackagePrefix;
	}
	
	@Column(name="LOCATION", nullable = false, length=100)
	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}

	@Column(name="BRANCH", length=100)
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
}
