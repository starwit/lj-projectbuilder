package de.starwit.dto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlRootElement;

import de.starwit.persistence.entity.AbstractEntity;
import de.starwit.persistence.entity.Relationship;

@XmlRootElement
public class EntityDto extends AbstractEntity<Long> {

    @NotEmpty
    private String name;

    @Valid
    private List<FieldDto> fields;

    @Valid
    private List<Relationship> relationships;

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
}
