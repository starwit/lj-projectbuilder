package de.starwit.generator.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.starwit.generator.dto.EntityDto;
import de.starwit.persistence.entity.Domain;

@Component
public class EntityMapper implements CustomMapper<Domain, EntityDto> {

    @Autowired
    private FieldMapper fieldMapper;

    @Override
    public EntityDto convertToDto(Domain entity) {
        EntityDto dto = new EntityDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setFields(fieldMapper.convertToDtoList(entity.getAttributes()));
        return dto;
    }

    @Override
    public Domain convertToEntity(EntityDto dto) {
        Domain entity = new Domain();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription("");
        entity.setAttributes(fieldMapper.convertToEntitySet(dto.getFields()));
        return entity;
    }


    
}
