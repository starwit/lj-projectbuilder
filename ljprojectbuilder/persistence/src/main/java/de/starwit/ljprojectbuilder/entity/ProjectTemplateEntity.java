package de.starwit.ljprojectbuilder.entity;

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
@Table(name="PROJECTTEMPLATE")
public class ProjectTemplateEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max=100)
	private String location = "https://github.com/witchpou/lirejarp.git";
	
	@NotNull
	@Pattern(regexp="^[A-Za-z0-9]*$")
	@Size(max=100)
	private String title = "lirejarp";
	
	@Size(max=250)
	private String description;
	
	@NotNull
	@Pattern(regexp="^[A-Za-z0-9]*$")
	@Size(max=100)
	private String packagePrefix = "starwit";
	
	@Size(max=100)
	@Pattern(regexp="^[A-Za-z0-9]*$")
	private String branch = "master";
	
	private Set<CodeTemplateEntity> codeTemplates;
	
	@Column(name="DESCRIPTION", length=250)
	public String getDescription() {
		return title;
	}

	public void setDescription(String title) {
		this.title = title;
	}
	
	@Column(name="TITLE", nullable=false, length=100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name="PREFIX", nullable=false, length=100)
	public String getPackagePrefix() {
		return packagePrefix;
	}

	public void setPackagePrefix(String packagePrefix) {
		this.packagePrefix = packagePrefix;
	}
	
	@Column(name="LOCATION", nullable = false, length=100)
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name="BRANCH", length=100)
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	@OrderBy("category, fileNameSuffix asc")
	@OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL, orphanRemoval=true, mappedBy = "projectTemplate")
	public Set<CodeTemplateEntity> getCodeTemplates() {
		return codeTemplates;
	}

	public void setCodeTemplates(Set<CodeTemplateEntity> codeTemplates) {
		this.codeTemplates = codeTemplates;
	}
}
