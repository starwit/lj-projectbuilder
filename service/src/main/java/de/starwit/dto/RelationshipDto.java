package de.starwit.dto;

import de.starwit.persistence.entity.AbstractEntity;

public class RelationshipDto extends AbstractEntity<Long> {

    private String relationshipName;
    private String relationshipType;
    private String otherEntityName;
    private String otherEotherEntityRelationshipName;

    public String getRelationshipName() {
        return relationshipName;
    }
    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }
    public String getRelationshipType() {
        return relationshipType;
    }
    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }
    public String getOtherEntityName() {
        return otherEntityName;
    }
    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }
    public String getOtherEntityRelationshipName() {
        return otherEotherEntityRelationshipName;
    }
    public void setOtherEntityRelationshipName(String otherEotherEntityRelationshipName) {
        this.otherEotherEntityRelationshipName = otherEotherEntityRelationshipName;
    }     
}
