package de.starwit.generator.mapper;

import org.springframework.stereotype.Component;

import de.starwit.generator.dto.FieldDto;
import de.starwit.persistence.entity.Attribute;

@Component
public class FieldMapper implements CustomMapper<Attribute, FieldDto> {

    @Override
    public FieldDto convertToDto(Attribute entity) {
        FieldDto dto = new FieldDto();
        dto.setId(entity.getId());
        dto.setFieldName(entity.getName());
        return dto;
    }

    @Override
    public Attribute convertToEntity(FieldDto dto) {
        Attribute entity = new Attribute();
        entity.setId(dto.getId());
        entity.setName(dto.getFieldName());
        return entity;
    }
    
}
