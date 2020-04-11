package de.starwit.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.entity.ProjectTemplateEntity;

public class ProjectDto extends AbstractEntity<Long> {

	@NotNull
	private ProjectTemplateEntity template;

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Size(max = 100)
	private String title;

	@NotNull
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Size(max = 100)
	private String packagePrefix;

	@Size(max = 100)
	private String targetPath;

	private String description;

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
}