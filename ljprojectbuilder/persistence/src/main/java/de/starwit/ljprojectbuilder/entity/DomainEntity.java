package de.starwit.ljprojectbuilder.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
@Entity
@Table(name="DOMAIN")
public class DomainEntity extends AbstractEntity {
	
	private static final long serialVersionUID = 1L;
	
	//domain attributes
	
	@NotBlank
	private String name;
	
	private String description;
	
	

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
}