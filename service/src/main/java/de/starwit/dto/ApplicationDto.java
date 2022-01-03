package de.starwit.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;

import de.starwit.persistence.entity.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
public class ApplicationDto extends AbstractEntity<Long> {

    @Schema(defaultValue = "defaultapp")
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$")
	@Length(max = 100)
    private String baseName;

    @Schema(defaultValue = "defaultpackage")
    @NotBlank
	@Pattern(regexp = "^[A-Za-z0-9]*$")
	@Length(max = 100)
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
