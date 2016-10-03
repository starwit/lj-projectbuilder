package de.starwit.ljprojectbuilder.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
@Entity
@Table(name="TEMPLATE")
public class TemplateEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max=100)
	private String templateLocation = "https://github.com/witchpou/lirejarp.git";
	
	@NotNull
	@Size(max=100)
	private String templateTitle = "lirejarp";
	
	@NotNull
	@Size(max=100)
	private String templatePackagePrefix = "starwit";
	
	@Column(name="TEMPLATE_TITLE", nullable=false, length=100)
	public String getTemplateTitle() {
		return templateTitle;
	}

	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}

	@Column(name="TEMPLATEPREFIX", nullable=false, length=100)
	public String getTemplatePackagePrefix() {
		return templatePackagePrefix;
	}

	public void setTemplatePackagePrefix(String templatePackagePrefix) {
		this.templatePackagePrefix = templatePackagePrefix;
	}
	
	@Column(name="TEMPLATELOCATION", nullable = false, length=100)
	public String getTemplateLocation() {
		return templateLocation;
	}

	public void setTemplateLocation(String templateLocation) {
		this.templateLocation = templateLocation;
	}
}
