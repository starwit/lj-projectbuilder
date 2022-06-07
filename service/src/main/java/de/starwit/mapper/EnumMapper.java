package de.starwit.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import de.starwit.dto.EnumDto;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.EnumDef;

@Component
public class EnumMapper implements CustomMapper<EnumDef, EnumDto> {

    @Override
    public EnumDto convertToDto(EnumDef entity) {
        EnumDto dto = new EnumDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setValue(entity.getValue());
        dto.setPosition(entity.getPosition());
        return dto;
    }

    @Override
    public EnumDef convertToEntity(EnumDto dto) {
        EnumDef entity = new EnumDef();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setValue(dto.getValue());
        entity.setPosition(dto.getPosition());
        return entity;
    }

    public List<EnumDef> addParent(List<EnumDef> entities, App parent) {
        if (entities != null) {
            for (EnumDef entity : entities) {
                entity.setApp(parent);
            }
        }
        return entities;
    }
}
