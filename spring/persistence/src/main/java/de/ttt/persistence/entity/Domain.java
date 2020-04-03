package de.ttt.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Domain Entity class
 */
@Entity
@Table(name = "DOMAIN")
public class Domain extends AbstractEntity<Long> {

//domain attributes
    @Column(name="ATTRIBUTE")
	private String attribute;
	
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
        
}
