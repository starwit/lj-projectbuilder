package de.starwit.generator.dto;

import java.util.List;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.entity.AppTemplate;

public class ApplicationDto extends AbstractEntity<Long> {

    private String baseName;
    private String packageName;
    private AppTemplate template;

    private List<EntityDto> entities;

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    
    public AppTemplate getTemplate() {
        return template;
    }

    public void setTemplate(AppTemplate template) {
        this.template = template;
    }

    public List<EntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDto> entities) {
        this.entities = entities;
    }

}
