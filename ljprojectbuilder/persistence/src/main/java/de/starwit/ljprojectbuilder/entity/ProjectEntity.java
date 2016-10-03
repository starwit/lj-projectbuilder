package de.starwit.ljprojectbuilder.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@Entity
@Table(name="PROJECT")
public class ProjectEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private TemplateEntity template;
	
	@NotNull
	@Size(max=100)
	private String title;

	@NotNull
	@Size(max=100)
	private String packagePrefix;
	
	@NotNull
	@Size(max=100)
	private String targetPath;
	
	private String description;
	
	private List<DomainEntity> domains;
	
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	public TemplateEntity getTemplate() {
		return template;
	}

	public void setTemplate(TemplateEntity template) {
		this.template = template;
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

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name="TARGETPATH", nullable = false, length=100)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	@XmlTransient
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="project")
	public List<DomainEntity> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainEntity> domains) {
		this.domains = domains;
	}
}