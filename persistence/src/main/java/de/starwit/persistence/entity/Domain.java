package de.starwit.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "DOMAIN")
public class Domain extends AbstractEntity<Long> {
	// domain attributes

	@NotBlank
	@Pattern(regexp = "^[A-Z][a-zA-Z0-9]*$")
	@Length(max = 100)
	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@Length(max = 240)
	@Column(name = "DESCRIPTION", length = 240)
	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "APP_ID")
	private App app;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Attribute> attributes = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Relationship> relationships = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

    public void setId(Long id) {
        this.id = id;
    }

	public List<Relationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<Relationship> relationships) {
		this.relationships = relationships;
	}

}