package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name="ATTRIBUTE")
public class AttributeEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String description;
	
	@NotBlank
	private String type;
	
	@NotBlank
	private String pattern;
	
	@NotNull
	private Integer max;
	
	@NotNull
	private Integer min;
	
	

	@Column(name="NAME", nullable = false)
	public String getName() {
		return name;
	}
	
		public void setName(String name) {
		this.name = name;
	}

	@Column(name="DESCRIPTION", nullable = false)
	public String getDescription() {
		return description;
	}
	
		public void setDescription(String description) {
		this.description = description;
	}

	@Column(name="TYPE", nullable = false)
	public String getType() {
		return type;
	}
	
		public void setType(String type) {
		this.type = type;
	}

	@Column(name="PATTERN", nullable = false)
	public String getPattern() {
		return pattern;
	}
	
		public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Column(name="MAX", nullable = false)
	public Integer getMax() {
		return max;
	}
	
		public void setMax(Integer max) {
		this.max = max;
	}

	@Column(name="MIN", nullable = false)
	public Integer getMin() {
		return min;
	}
	
		public void setMin(Integer min) {
		this.min = min;
	}
}