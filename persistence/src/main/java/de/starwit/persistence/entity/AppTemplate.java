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
@Table(name = "APPTEMPLATE")
public class AppTemplate extends AbstractEntity<Long> {


	@NotNull
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Size(max = 100)
	@Column(name = "TEMPLATE_NAME", nullable = false, length = 100)
	private String templateName = "lirejarp";


	@NotNull
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Size(max = 100)
	@Column(name = "PACKAGE", nullable = false, length = 100)
	private String packagePlaceholder = "starwit";

	@NotNull
	@Pattern(regexp = "^(?:git|ssh|https?|git@[-w.]+):(//)?(.*?)(.git)(/?|#[-dw._]+?)$")
	@Size(max = 100)
	@Column(name = "LOCATION", nullable = false, length = 100)
	private String location;

	@Size(max = 100)
	@Column(name = "BRANCH", length = 100)
	private String branch = "master";

	@Column(name = "CREDENTIALS_REQUIRED")
	private boolean credentialsRequired = false;

	@Column(name = "DESCRIPTION")
	private String description;

	@OrderBy("category, fileNameSuffix asc")
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "appTemplate")
	private Set<TemplateFile> templateFiles;

    public void setId(Long id) {
        this.id = id;
    }

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getPackagePlaceholder() {
		return packagePlaceholder;
	}

	public void setPackagePlaceholder(String packagePlaceholder) {
		this.packagePlaceholder = packagePlaceholder;
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
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<TemplateFile> getTemplateFiles() {
		return templateFiles;
	}

	public void setTemplateFiles(Set<TemplateFile> templateFiles) {
		this.templateFiles = templateFiles;
	}
}
