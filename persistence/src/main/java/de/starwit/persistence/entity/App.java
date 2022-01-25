package de.starwit.persistence.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import de.starwit.persistence.converter.ListToStringConverter;

@XmlRootElement
@Entity
@Table(name = "APP")
public class App extends AbstractEntity<Long> {

	@NotNull
	@ManyToOne
	@JoinColumn(name = "TEMPLATE_ID", nullable = false)
	private AppTemplate template;

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Length(max = 100)
	@Column(name = "TITLE", nullable = false, length = 100)
	private String title;

	@NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Length(max = 100)
	@Column(name = "PACKAGEPREFIX", nullable = false, length = 100)
	private String packagePrefix;

	@Length(max = 100)
	@Column(name = "TARGETPATH", length = 100)
	private String targetPath;

	@Length(max = 240)
	@Column(name = "DESCRIPTION", length = 240)
	private String description;

	@OneToMany(mappedBy = "app",  cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Domain> domains;

	@Column( name = "GROUPS")
	@Convert(converter = ListToStringConverter.class)
	private List<String> groups;

	@Override
    public void setId(Long id) {
        this.id = id;
    }
	
	public AppTemplate getTemplate() {
		return template;
	}

	public void setTemplate(AppTemplate template) {
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

	public List<Domain> getDomains() {
		return domains;
	}

	public void setDomains(List<Domain> domains) {
		this.domains = domains;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}
}