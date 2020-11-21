package de.starwit.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Entity implementation class for Entity: CategoryEntity
 *
 */
@XmlRootElement
@JsonIgnoreProperties(value = { "templates" })
@Entity
@Table(name = "CATEGORY")
public class CategoryEntity extends AbstractEntity<Long> {

	public static final String DEFAULT_CATEGORY = "ENTITY";

	@NotNull
	private String name = DEFAULT_CATEGORY;

	@JsonBackReference
	@OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
	private List<CodeTemplateEntity> templates;

	@Column(name = "NAME", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CodeTemplateEntity> getTemplates() {
		return templates;
	}

	public void setTemplates(List<CodeTemplateEntity> templates) {
		this.templates = templates;
	}

}
