package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.validator.constraints.Length;

@XmlRootElement
@Entity
@Table(name = "TEMPLATEFILE")
public class TemplateFile extends AbstractEntity<Long> {

	@Length(max = 100)
	@NotBlank
	@Column(name = "FILE_NAME_S", nullable = false, length = 100)
	private String fileName;

	@NotBlank
	@Column(name = "TEMPLATE_PATH", nullable = false)
	private String templatePath;

	@JsonIgnore
	@Transient
	private String concreteTemplatePath = "";

	@NotBlank
	private String targetPath;

	@JsonIgnore
	@Transient
	private String concreteTargetPath = "";

	@Column(name = "APPEND_TO_FILE")
	private boolean append = false;

	@NotBlank
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Column(name = "CATEGORY", nullable = false)
	private String category = "default";

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="APPTEMPLATE_ID")
	private AppTemplate appTemplate;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public AppTemplate getAppTemplate() {
		return appTemplate;
	}

	public void setAppTemplate(AppTemplate appTemplate) {
		this.appTemplate = appTemplate;
	}

	@XmlTransient
	public String getConcreteTargetPath() {
		return concreteTargetPath;
	}

	public void setConcreteTargetPath(String concreteTargetPath) {
		this.concreteTargetPath = concreteTargetPath;
	}

	@XmlTransient
	public String getConcreteTemplatePath() {
		return concreteTemplatePath;
	}

	public void setConcreteTemplatePath(String concreteTemplatePath) {
		this.concreteTemplatePath = concreteTemplatePath;
	}

	public boolean isAppend() {
		return append;
	}

	public void setAppend(boolean append) {
		this.append = append;
	}
}
