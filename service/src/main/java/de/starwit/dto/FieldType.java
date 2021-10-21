package de.starwit.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum FieldType {
    String(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.minlength, FieldValidateRulesType.maxlength, FieldValidateRulesType.pattern, FieldValidateRulesType.unique}),
    Integer(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.min, FieldValidateRulesType.max, FieldValidateRulesType.unique}),
    Long(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.min, FieldValidateRulesType.max, FieldValidateRulesType.unique}),
    BigDecimal(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.min, FieldValidateRulesType.max, FieldValidateRulesType.unique}),
    Float(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.min, FieldValidateRulesType.max, FieldValidateRulesType.unique}),
    Double(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.min, FieldValidateRulesType.max, FieldValidateRulesType.unique}),
    Boolean(new FieldValidateRulesType[]{FieldValidateRulesType.required, FieldValidateRulesType.unique});



    private final FieldValidateRulesType[] validation;

    private FieldType(FieldValidateRulesType[] validation) {
        this.validation = validation;
    }

    public FieldValidateRulesType[] getValidationRules() {
        return validation;
    }
}
