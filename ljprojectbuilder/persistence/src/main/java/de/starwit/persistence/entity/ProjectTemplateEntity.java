package de.starwit.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "PROJECTTEMPLATE")
public class ProjectTemplateEntity extends AbstractEntity<Long> {

	@NotNull
	@Size(max = 100)
	@Column(name = "LOCATION", nullable = false, length = 100)
	private String location;

	@NotNull
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Size(max = 100)
	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@Column(name = "DESCRIPTION")
	private String description;

	@NotNull
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Size(max = 100)
	@Column(name = "PREFIX", nullable = false, length = 100)
	private String packagePrefix;

	@Size(max = 100)
	@Column(name = "BRANCH", length = 100)
	private String branch = "master";

	@Column(name = "CREDENTIALS_REQUIRED")
	private boolean credentialsRequired = false;

	@OrderBy("category, fileNameSuffix asc")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "projectTemplate")
	private Set<CodeTemplateEntity> codeTemplates;

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

	public Set<CodeTemplateEntity> getCodeTemplates() {
		return codeTemplates;
	}

	public void setCodeTemplates(Set<CodeTemplateEntity> codeTemplates) {
		this.codeTemplates = codeTemplates;
	}
}
