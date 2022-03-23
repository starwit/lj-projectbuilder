package de.starwit.dto;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.entity.Position;
import de.starwit.persistence.entity.Relationship;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Schema
@XmlRootElement
public class EntityDto extends AbstractEntity<Long> {

    @Schema(defaultValue = "DefaultEntity")
    @NotBlank
    @Pattern(regexp = "^[A-Z][a-zA-Z0-9]*$")
    @Length(max = 100)
    private String name;

    @Valid
    private List<FieldDto> fields;

    @Valid
    private List<Relationship> relationships;

    @Valid
    private Position position;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<FieldDto> getFields() {
        return fields;
    }

    public void setFields(List<FieldDto> fields) {
        this.fields = fields;
    }

    public List<Relationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<Relationship> relationships) {
        this.relationships = relationships;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
