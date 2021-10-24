package de.starwit.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;

@XmlRootElement
public class ApplicationDto extends AbstractEntity<Long> {

    @NotEmpty
    private String baseName;

    @NotEmpty
    private String packageName;

    @Valid
    private AppTemplateDto template;

    @Valid
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

    
    public AppTemplateDto getTemplate() {
        return template;
    }

    public void setTemplate(AppTemplateDto template) {
        this.template = template;
    }

    public List<EntityDto> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityDto> entities) {
        this.entities = entities;
    }

}
