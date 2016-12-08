package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name="ATTRIBUTE")
public class AttributeEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	private DataType dataType;
	
	private String pattern;
	
	private Integer max;
	
	private Integer min;
	
	private boolean nullable = true;

	@Column(name="NAME", nullable = false)
	public String getName() {
		return name;
	}
	
		public void setName(String name) {
		this.name = name;
	}

	@Column(name="DESCRIPTION")
	public String getDescription() {
		return description;
	}
	
		public void setDescription(String description) {
		this.description = description;
	}

	@Enumerated(EnumType.STRING)
	@Column(name="TYPE", nullable = false)
	public DataType getDataType() {
		return dataType;
	}
	
	public void setDataType(DataType type) {
		this.dataType = type;
	}

	@Column(name="PATTERN")
	public String getPattern() {
		return pattern;
	}
	
		public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Column(name="MAXIMUM")
	public Integer getMax() {
		return max;
	}
	
		public void setMax(Integer max) {
		this.max = max;
	}

	@Column(name="MINIMUM")
	public Integer getMin() {
		return min;
	}
	
	public void setMin(Integer min) {
		this.min = min;
	}

	@Column(name="NULLABLE")
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}
	
	@Transient
	public boolean isRequired() {
		return !nullable;
	}

	public void setRequired(boolean required) {
		this.nullable = !required;
	}
}