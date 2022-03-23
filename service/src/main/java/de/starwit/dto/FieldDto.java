package de.starwit.dto;

import de.starwit.persistence.entity.AbstractEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Schema
@XmlRootElement
public class FieldDto extends AbstractEntity<Long> {

    @Schema(defaultValue = "defaultField")
    @NotBlank
    @Pattern(regexp = "^[a-z][a-zA-Z0-9]*$")
    @Length(max = 100)
    private String fieldName;

    @Schema(defaultValue = "String")
    @NotNull
    private FieldType fieldType;

    private List<FieldValidateRulesType> fieldValidateRules;
    private Integer fieldValidateRulesMin;
    private Integer fieldValidateRulesMax;
    private Integer fieldValidateRulesMinlength;
    private Integer fieldValidateRulesMaxlength;
    private String fieldValidateRulesPattern;
    private String fieldValidateRulesPatternJava;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public List<FieldValidateRulesType> getFieldValidateRules() {
        return fieldValidateRules;
    }

    public void setFieldValidateRules(List<FieldValidateRulesType> fieldValidateRules) {
        this.fieldValidateRules = fieldValidateRules;
    }

    public Integer getFieldValidateRulesMin() {
        return fieldValidateRulesMin;
    }

    public void setFieldValidateRulesMin(Integer fieldValidateRulesMin) {
        this.fieldValidateRulesMin = fieldValidateRulesMin;
    }

    public Integer getFieldValidateRulesMax() {
        return fieldValidateRulesMax;
    }

    public void setFieldValidateRulesMax(Integer fieldValidateRulesMax) {
        this.fieldValidateRulesMax = fieldValidateRulesMax;
    }

    public Integer getFieldValidateRulesMinlength() {
        return fieldValidateRulesMinlength;
    }

    public void setFieldValidateRulesMinlength(Integer fieldValidateRulesMinlength) {
        this.fieldValidateRulesMinlength = fieldValidateRulesMinlength;
    }

    public Integer getFieldValidateRulesMaxlength() {
        return fieldValidateRulesMaxlength;
    }

    public void setFieldValidateRulesMaxlength(Integer fieldValidateRulesMaxlength) {
        this.fieldValidateRulesMaxlength = fieldValidateRulesMaxlength;
    }

    public String getFieldValidateRulesPattern() {
        return fieldValidateRulesPattern;
    }

    public void setFieldValidateRulesPattern(String fieldValidateRulesPattern) {
        this.fieldValidateRulesPattern = fieldValidateRulesPattern;
    }

    public String getFieldValidateRulesPatternJava() {
        return fieldValidateRulesPatternJava;
    }

    public void setFieldValidateRulesPatternJava(String fieldValidateRulesPatternJava) {
        this.fieldValidateRulesPatternJava = fieldValidateRulesPatternJava;
    }

}
