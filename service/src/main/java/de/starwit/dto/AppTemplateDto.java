package de.starwit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;

@XmlRootElement
public class AppTemplateDto extends AbstractEntity<Long> {

    @NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&()*./_-]{0,100}$")
	@Size(max = 100)
    private String name = "default";

    private boolean credentialsRequired = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCredentialsRequired() {
        return credentialsRequired;
    }

    public void setCredentialsRequired(boolean credentialsRequired) {
        this.credentialsRequired = credentialsRequired;
    }
    
}
