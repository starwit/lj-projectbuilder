package de.starwit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
public class EnumDto extends AbstractEntity<Long> {

    @Schema(defaultValue = "SomeType")
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    @Size(max = 200)
    private String name;

    @Schema(defaultValue = "one,two,three")
    @NotBlank
    @Size(max = 500)
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String[] getSelectList() {
        return value.split(",");
    }
}
