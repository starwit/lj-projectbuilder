package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * AppTemplate Entity class
 */
@Entity
@Table(name = "APPTEMPLATE")
public class AppTemplate extends AbstractEntity<Long> {

//domain attributes
    @Column(name="NAME")
	private String name;
	
    @Column(name="TEMPLATEFILES")
	private String templatefiles;
	
    @Column(name="DESCRIPTION")
	private String description;
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
        
    public String getTemplatefiles() {
        return templatefiles;
    }

    public void setTemplatefiles(String templatefiles) {
        this.templatefiles = templatefiles;
    }
        
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
        
}
