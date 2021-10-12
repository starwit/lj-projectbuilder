package de.starwit.generator.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import de.starwit.generator.dto.FieldDto;
import de.starwit.generator.dto.FieldType;
import de.starwit.generator.dto.FieldValidateRulesType;
import de.starwit.persistence.entity.Attribute;
import de.starwit.persistence.entity.DataType;

@Component
public class FieldMapper implements CustomMapper<Attribute, FieldDto> {

    @Override
    public FieldDto convertToDto(Attribute entity) {
        FieldDto dto = new FieldDto();
        dto.setId(entity.getId());
        dto.setFieldName(entity.getName());
        dto.setFieldType(FieldType.valueOf(entity.getDataType().toString()));
        setValidationToDto(dto, entity);
        return dto;
    }

    @Override
    public Attribute convertToEntity(FieldDto dto) {
        Attribute entity = new Attribute();
        entity.setId(dto.getId());
        entity.setName(dto.getFieldName());
        entity.setDataType(DataType.valueOf(dto.getFieldType().toString()));
        setValidationToEntity(dto, entity);
        return entity;
    }

    private void setValidationToEntity(FieldDto dto, Attribute entity){
        if (dto == null || dto.getFieldType() == null || dto.getFieldValidateRules() == null) {
            return;
        }

        FieldValidateRulesType[] allowedRules = dto.getFieldType().getValidationRules();
        List<FieldValidateRulesType> rules = dto.getFieldValidateRules();
        for (FieldValidateRulesType allowedRule : allowedRules) {
            switch (allowedRule) {
                case min:
                    if(rules.contains(allowedRule)) {
                        entity.setMin(dto.getFieldValidateRulesMin());
                    }
                    break;                
                case max:
                    if(rules.contains(allowedRule)) {
                        entity.setMax(dto.getFieldValidateRulesMax());
                    }
                    break;
                case minlength:
                    if(rules.contains(allowedRule)) {
                        entity.setMin(dto.getFieldValidateRulesMinlength());
                    }
                    break;                    
                case maxlength:
                    if(rules.contains(allowedRule)) {
                        entity.setMax(dto.getFieldValidateRulesMaxlength());
                    }
                    break;
                case pattern:
                    if(rules.contains(allowedRule)) {
                        entity.setPattern(dto.getFieldValidateRulesPattern());
                        entity.setPatterJava(dto.getFieldValidateRulesPatternJava());
                    }
                    break;
                case required:
                    if(rules.contains(allowedRule)) {
                        entity.setNullable(false);
                    }
                    break;
                case unique:
                    if(rules.contains(allowedRule)) {
                        entity.setUnique(true);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void setValidationToDto(FieldDto dto, Attribute entity) {
        if (entity == null || entity.getDataType() == null) {
            return;
        }

        FieldValidateRulesType[] allowedRules = dto.getFieldType().getValidationRules();
        List<FieldValidateRulesType> rules = new ArrayList<>();

        for (FieldValidateRulesType allowedRule : allowedRules) {
            switch (allowedRule) {
                case min:
                    if(entity.getMin() != null) {
                        rules.add(FieldValidateRulesType.min);
                        dto.setFieldValidateRulesMin(entity.getMin());
                    }
                    break;                
                case max:
                    if(entity.getMax() != null) {
                        rules.add(FieldValidateRulesType.max);
                        dto.setFieldValidateRulesMax(entity.getMax());
                    }
                    break;
                case minlength:
                    if(entity.getMin() != null) {
                        rules.add(FieldValidateRulesType.minlength);
                        dto.setFieldValidateRulesMinlength(entity.getMin());
                    }
                    break;
                case maxlength:
                    if(entity.getMax() != null) {
                        rules.add(FieldValidateRulesType.maxlength);
                        dto.setFieldValidateRulesMaxlength(entity.getMax());
                    }
                    break;
                case pattern:
                    if(entity.getPattern() != null) {
                       rules.add(FieldValidateRulesType.pattern);
                       dto.setFieldValidateRulesPattern(entity.getPattern());
                       dto.setFieldValidateRulesPatternJava(entity.getPatterJava());
                    }
                    break;            
                case required:
                    if(entity.isRequired()) {
                        rules.add(FieldValidateRulesType.required);
                    }
                    break;
                case unique:
                    if(entity.isUnique()) {
                        rules.add(FieldValidateRulesType.unique);
                    }
                    break;
                default:
                    break;
            }
        }
        if (rules.size() > 0) {
            dto.setFieldValidateRules(rules);
        }
    }
    
}
