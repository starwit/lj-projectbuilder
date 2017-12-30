package de.starwit.ljprojectbuilder.entity;

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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "CODETEMPLATE")
public class CodeTemplateEntity extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String fileNameSuffix = ".java";
	
	@NotNull
	private String templatePath = "";
	
	@NotNull
	private String targetPath = "";
	
	private String concreteTargetPath = "";
	
	@NotNull
	private boolean createDomainDir = false;
	
	@NotNull
	private boolean upperCaseFirst = false;
	
	@NotNull
	private boolean lowerCase = false;
	
	@NotNull
	private TemplateType type = TemplateType.DOMAIN;
	
	@NotNull
	private CategoryEntity category;

	private Set<ProjectEntity> projects;

	@Column(name="FILE_NAME_SUFFIX", nullable = false, length=100)
	public String getFileNameSuffix() {
		return fileNameSuffix;
	}

	public void setFileNameSuffix(String fileNameSuffix) {
		this.fileNameSuffix = fileNameSuffix;
	}

	@Column(name="TEMPLATE_PATH", nullable = false)
	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(String templatePath) {
		this.templatePath = templatePath;
	}

	@Column(name="TARGET_PATH", nullable = false)
	public String getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	@Column(name="CREATE_DOMAIN_DIR", nullable = false)
	public boolean isCreateDomainDir() {
		return createDomainDir;
	}

	public void setCreateDomainDir(boolean createDomainDir) {
		this.createDomainDir = createDomainDir;
	}

	@Column(name="FIRST_UPPER", nullable = false)
	public boolean isUpperCaseFirst() {
		return upperCaseFirst;
	}

	public void setUpperCaseFirst(boolean upperCaseFirst) {
		this.upperCaseFirst = upperCaseFirst;
	}
	
	@Column(name="LOWER_CASE", nullable = false)
	public boolean isLowerCase() {
		return lowerCase;
	}

	public void setLowerCase(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="TEMPLATE_TYPE", nullable = false)
	public TemplateType getType() {
		return type;
	}

	public void setType(TemplateType type) {
		this.type = type;
	}

	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID", nullable = false)
	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}
	
    @ManyToMany
    @JoinTable(name="CODETEMPLATE_PROJECT",
        joinColumns=
            @JoinColumn(name="CODETEMPLATE_ID", referencedColumnName="ID"),
        inverseJoinColumns=
            @JoinColumn(name="PROJECT_ID", referencedColumnName="ID")
        )
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
			checkedDir = checkOrCreateDir(domainname.toLowerCase()) ;
		}
		
		if (upperCaseFirst) {
			domainname = CodeTemplateEntity.upperCaseFirst(domainname);
		} else if (lowerCase) {
			domainname = domainname.toLowerCase();
		}
		if (checkedDir != null) {
			String targetUrl = checkedDir  + domainname + fileNameSuffix ;
			return targetUrl ;
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

	@Transient
	public String getConcreteTargetPath() {
		return concreteTargetPath;
	}

	public void setConcreteTargetPath(String concreteTargetPath) {
		this.concreteTargetPath = concreteTargetPath;
	}
}
