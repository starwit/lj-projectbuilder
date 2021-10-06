package de.starwit.generator.dto;

import java.util.List;

import de.starwit.persistence.entity.AbstractEntity;

public class EntityDto extends AbstractEntity<Long> {

    private String name;
    private List<FieldDto> fields;
    private List<RelationshipDto> relationships;

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
    public List<RelationshipDto> getRelationships() {
        return relationships;
    }
    public void setRelationships(List<RelationshipDto> relationships) {
        this.relationships = relationships;
    }
}
