package de.starwit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;

@XmlRootElement
public class AppTemplateDto extends AbstractEntity<Long> {

    @NotBlank
	@Pattern(regexp = "^([a-zA-Z_0-9]|-)*$")
	@Size(max = 100)
    private String name = "default";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
