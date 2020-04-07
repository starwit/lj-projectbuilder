package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import javax.validation.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name = "ATTRIBUTE")
public class AttributeEntity extends AbstractEntity<Long> {

	// domain attributes

	@NotBlank
	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private DataType dataType;

	@Column(name = "PATTERN")
	private String pattern;

	@Column(name = "MAXIMUM")
	private Integer max;

	@Column(name = "MINIMUM")
	private Integer min;

	@Transient
	@Column(name = "NULLABLE")
	private boolean nullable = true;

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

	public DataType getDataType() {
		return dataType;
	}

	public void setDataType(DataType type) {
		this.dataType = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public boolean isRequired() {
		return !nullable;
	}

	public void setRequired(boolean required) {
		this.nullable = !required;
	}
}