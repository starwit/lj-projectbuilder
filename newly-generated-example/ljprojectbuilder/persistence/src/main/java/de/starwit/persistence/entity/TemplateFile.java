package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * TemplateFile Entity class
 */
@Entity
@Table(name = "TEMPLATEFILE")
public class TemplateFile extends AbstractEntity<Long> {

//domain attributes
    @Column(name="DESCRIPTION")
	private String description;
	
    @Column(name="NAME")
	private String name;
	
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
}
