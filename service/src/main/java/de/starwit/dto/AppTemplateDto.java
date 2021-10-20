package de.starwit.dto;

import de.starwit.persistence.entity.AbstractEntity;

public class AppTemplateDto extends AbstractEntity<Long> {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
