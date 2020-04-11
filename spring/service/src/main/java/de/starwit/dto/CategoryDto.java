package de.starwit.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import de.starwit.persistence.entity.AbstractEntity;

public class CategoryDto extends AbstractEntity<Long> {

	public static final String DEFAULT_CATEGORY = "ENTITY";
	private Long id;

	@NotNull
	private String name = DEFAULT_CATEGORY;

	@Column(name = "NAME", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
}
