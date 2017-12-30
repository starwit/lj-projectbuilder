package de.starwit.ljprojectbuilder.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties({"domains","selectedDomains"})
@Entity
@Table(name="PROJECT")
public class ProjectEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull
	private ProjectTemplateEntity template;
	
	@NotNull
	@Pattern(regexp="^[A-Za-z0-9]*$")
	@Size(max=100)
	private String title;

	@NotNull
	@Pattern(regexp="^[A-Za-z0-9]*$")
	@Size(max=100)
	private String packagePrefix;
	
	@Size(max=100)
	private String targetPath;
	
	private String description;
	
	private List<DomainEntity> domains;

	private Set<DomainEntity> selectedDomains;
	
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	public ProjectTemplateEntity getTemplate() {
		return template;
	}

	public void setTemplate(ProjectTemplateEntity template) {
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
	
	@Column(name="TARGETPATH", length=100)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	@XmlTransient
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "PROJECT_ID")
	public List<DomainEntity> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainEntity> domains) {
		this.domains = domains;
	}
	
	@XmlTransient
	@Transient
	public Set<DomainEntity> getSelectedDomains() {
		if (selectedDomains == null && getDomains() != null) {
			selectedDomains =  getDomains().stream()                // convert list to stream
                .filter(domain -> domain.isSelected())     // we only want selected domains
                .collect(Collectors.toSet());
		}
		return selectedDomains;
	}
}