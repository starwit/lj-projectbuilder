package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * App Entity class
 */
@Entity
@Table(name = "APP")
public class App extends AbstractEntity<Long> {

//domain attributes
    @Column(name="NAME")
	private String name;
	
    @Column(name="DESCRIPTION")
	private String description;
	
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
        
}
