package de.starwit.generator.dto;

import java.util.List;

import de.starwit.persistence.entity.AbstractEntity;

public class FieldDto extends AbstractEntity<Long> {
    
    private String fieldName;
    private String fieldType;
    private List<String> fieldValidateRules;
    
    public String getFieldName() {
        return fieldName;
    }
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    public String getFieldType() {
        return fieldType;
    }
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
    public List<String> getFieldValidateRules() {
        return fieldValidateRules;
    }
    public void setFieldValidateRules(List<String> fieldValidateRules) {
        this.fieldValidateRules = fieldValidateRules;
    }

}
