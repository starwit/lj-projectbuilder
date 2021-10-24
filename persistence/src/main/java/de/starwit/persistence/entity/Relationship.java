package de.starwit.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
@XmlRootElement
@Entity
@Table(name = "RELATIONSHIP")
public class Relationship extends AbstractEntity<Long> {

    @Schema(required = true, allowableValues = { "one-to-one",
    "many-to-one", "many-to-many", "one-to-one"}, defaultValue = "one-to-many")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE", nullable = false)
    private RelationshipType relationshipType = RelationshipType.OneToMany;

    @NotBlank
    @Column(name = "OTHER_ENTITY_NAME", nullable = false)
    private String otherEntityName;

    @Column(name = "OTHER_ENTITY_RELATIONNAME")
    private String otherEntityRelationshipName;

    @NotNull
    @Column(name = "NAME", nullable = false)
    private String relationshipName;

    @Column(name = "OWNER")
    private boolean ownerSide = false;

 
    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    @JsonIgnore
    public RelationshipType getRelationshipType() {
        return relationshipType;
    }

    @JsonIgnore
    public void setRelationshipType(RelationshipType relationshipType) {
        this.relationshipType = relationshipType;
    }

    @Schema(required = true, allowableValues = { "one-to-one",
    "many-to-one", "many-to-many", "one-to-one"}, defaultValue = "one-to-many")
    @JsonProperty("relationshipType")
    public String getRelationshipTypeAsJson() {
        return relationshipType.getJsonFormat();
    }

    @Schema(required = true, allowableValues = { "one-to-one",
    "many-to-one", "many-to-many", "one-to-one"}, defaultValue = "one-to-many")
    @JsonProperty("relationshipType")
    public void setRelationshipTypeAsJson(String relationshipType) {
        this.relationshipType = RelationshipType.setJsonFormat(relationshipType);
    }

    public String getOtherEntityName() {
        return otherEntityName;
    }
    
    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public String getOtherEntityRelationshipName() {
        return otherEntityRelationshipName;
    }

    public void setOtherEntityRelationshipName(String otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
    }

    public boolean isOwnerSide() {
        return ownerSide;
    }

    public void setOwnerSide(boolean ownerSide) {
        this.ownerSide = ownerSide;
    }
 
}
