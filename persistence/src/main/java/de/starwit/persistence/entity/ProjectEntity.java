package de.starwit.persistence.entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "PROJECT")
public class ProjectEntity extends AbstractEntity<Long> {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	private ProjectTemplateEntity template;

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Size(max = 100)
	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Size(max = 100)
	@Column(name = "PACKAGEPREFIX", nullable = false, length = 100)
	private String packagePrefix;

	@Size(max = 100)
	@Column(name = "TARGETPATH", length = 100)
	private String targetPath;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(mappedBy = "project",  cascade = CascadeType.ALL, orphanRemoval = true)
	private List<DomainEntity> domains;

	@Transient
	private Set<DomainEntity> selectedDomains;

    public void setId(Long id) {
        this.id = id;
    }
	
	public ProjectTemplateEntity getTemplate() {
		return template;
	}

	public void setTemplate(ProjectTemplateEntity template) {
		this.template = template;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public List<DomainEntity> getDomains() {
		return domains;
	}

	public void setDomains(List<DomainEntity> domains) {
		this.domains = domains;
	}

	public Set<DomainEntity> getSelectedDomains() {
		if (selectedDomains == null && getDomains() != null) {
			selectedDomains = getDomains().stream() // convert list to stream
					.filter(domain -> domain.isSelected()) // we only want selected domains
					.collect(Collectors.toSet());
		}
		return selectedDomains;
	}

}