package de.starwit.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
@Entity
@Table(name = "APPTEMPLATE")
public class AppTemplate extends AbstractEntity<Long> {

	@NotBlank
	@Length(max = 100)
	@Column(name = "LOCATION", nullable = false, length = 100)
	private String location;

	@NotBlank
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Length(max = 100)
	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@NotBlank
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Length(max = 100)
	@Column(name = "PREFIX", nullable = false, length = 100)
	private String packagePrefix;

	@Size(max = 100)
	@Column(name = "BRANCH", length = 100)
	private String branch = "master";

	@Column(name = "CREDENTIALS_REQUIRED")
	private boolean credentialsRequired = false;

	@OrderBy("category, fileNameSuffix asc")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "appTemplate")
	private Set<TemplateFile> templateFiles;

    public void setId(Long id) {
        this.id = id;
    }
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPackagePrefix() {
		return packagePrefix;
	}

	public void setPackagePrefix(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public boolean isCredentialsRequired() {
		return credentialsRequired;
	}

	public void setCredentialsRequired(boolean credentialsRequired) {
		this.credentialsRequired = credentialsRequired;
	}

	public Set<TemplateFile> getTemplateFiles() {
		return templateFiles;
	}

	public void setTemplateFiles(Set<TemplateFile> templateFiles) {
		this.templateFiles = templateFiles;
	}
}
