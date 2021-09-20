package de.starwit.persistence.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "DOMAIN")
public class DomainEntity extends AbstractEntity<Long> {

	@Transient
	private Long appId;

	// domain attributes

	@NotBlank
	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "APP_ID")
	private App app;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<AttributeEntity> attributes;

	@Column(name = "SELECTED", nullable = false)
	private boolean selected = true;

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

	public Set<AttributeEntity> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<AttributeEntity> attributes) {
		this.attributes = attributes;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

    public void setId(Long id) {
        this.id = id;
    }
}