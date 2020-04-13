package de.starwit.persistence.entity;

import java.io.File;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.Length;

@XmlRootElement
@JsonIgnoreProperties("projectTemplate, projects")
@Entity
@Table(name = "CODETEMPLATE")
public class CodeTemplateEntity extends AbstractEntity<Long> {

	@Length(max = 100)
	@NotBlank
	@Column(name = "FILE_NAME_SUFFIX", nullable = false, length = 100)
	private String fileNameSuffix;

	@NotBlank
	@Column(name = "TEMPLATE_PATH", nullable = false)
	private String templatePath;

	@Transient
	private String concreteTemplatePath = "";

	@NotBlank
	@Column(name = "TARGET_PATH", nullable = false)
	private String targetPath;

	@Transient
	private String concreteTargetPath = "";

	@Column(name = "CREATE_DOMAIN_DIR", nullable = false)
	private boolean createDomainDir = false;

	@Column(name = "FIRST_UPPER", nullable = false)
	private boolean upperCaseFirst = false;

	@Column(name = "LOWER_CASE", nullable = false)
	private boolean lowerCase = false;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TEMPLATE_TYPE", nullable = false)
	private TemplateType type = TemplateType.DOMAIN;

	@JsonManagedReference
	@NotNull
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private CategoryEntity category;

	@JsonBackReference
	@ManyToMany
	@JoinTable(name = "CODETEMPLATE_PROJECT", joinColumns = @JoinColumn(name = "CODETEMPLATE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "PROJECT_ID", referencedColumnName = "ID"))
	private Set<ProjectEntity> projects;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "PROJECTTEMPLATE_ID")
	private ProjectTemplateEntity projectTemplate;

	public String getFileNameSuffix() {
		return fileNameSuffix;
	}

	public void setFileNameSuffix(String fileNameSuffix) {
		this.fileNameSuffix = fileNameSuffix;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public boolean isCreateDomainDir() {
		return createDomainDir;
	}

	public void setCreateDomainDir(boolean createDomainDir) {
		this.createDomainDir = createDomainDir;
	}

	public boolean isUpperCaseFirst() {
		return upperCaseFirst;
	}

	public void setUpperCaseFirst(boolean upperCaseFirst) {
		this.upperCaseFirst = upperCaseFirst;
	}

	public boolean isLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	
	public ProjectTemplateEntity getProjectTemplate() {
		return projectTemplate;
	}

	public void setProjectTemplate(ProjectTemplateEntity projectTemplate) {
		this.projectTemplate = projectTemplate;
	}

	public Set<ProjectEntity> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectEntity> projects) {
		this.projects = projects;
	}

	@XmlTransient
	@Transient
	public String getTargetFileUrl(String domainname) {
		String checkedDir = concreteTargetPath;
		if (createDomainDir) {
			checkedDir = checkOrCreateDir(domainname.toLowerCase());
		}

		if (upperCaseFirst) {
			domainname = CodeTemplateEntity.upperCaseFirst(domainname);
		} else if (lowerCase) {
			domainname = domainname.toLowerCase();
		}
		if (checkedDir != null) {
			String targetUrl = checkedDir + domainname + fileNameSuffix;
			return targetUrl;
		}
		return null;
	}

	private String checkOrCreateDir(String domainDir) {
		File checkedDir = new File(concreteTargetPath + domainDir);
		boolean success = true;
		if (!checkedDir.exists()) {
			success = checkedDir.mkdirs();
		}
		if (success) {
			return checkedDir.getPath() + System.getProperty("file.separator");
		}
		return null;
	}

	public static String upperCaseFirst(String value) {
		// Convert String to char array.
		char[] array = value.toCharArray();
		// Modify first element in array.
		array[0] = Character.toUpperCase(array[0]);
		// Return string.
		return new String(array);
	}

	public String getConcreteTargetPath() {
		return concreteTargetPath;
	}

	public void setConcreteTargetPath(String concreteTargetPath) {
		this.concreteTargetPath = concreteTargetPath;
	}

	public String getConcreteTemplatePath() {
		return concreteTemplatePath;
	}

	public void setConcreteTemplatePath(String concreteTemplatePath) {
		this.concreteTemplatePath = concreteTemplatePath;
	}
}
