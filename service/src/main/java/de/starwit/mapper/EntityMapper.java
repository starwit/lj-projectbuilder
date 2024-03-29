package de.starwit.mapper;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.starwit.dto.EntityDto;
import de.starwit.persistence.entity.App;
import de.starwit.persistence.entity.Attribute;
import de.starwit.persistence.entity.Domain;

@Component
public class EntityMapper implements CustomMapper<Domain, EntityDto> {

    @Autowired
    private FieldMapper fieldMapper;

    @Autowired
    private EnumMapper enumMapper;

    @Override
    public EntityDto convertToDto(Domain entity) {
        EntityDto dto = new EntityDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        if (entity.getAttributes() != null && !entity.getAttributes().isEmpty()) {
            dto.setFields(fieldMapper.convertToDtoList(entity.getAttributes()));
        }
        if (entity.getRelationships() != null && !entity.getRelationships().isEmpty()) {
            dto.setRelationships(entity.getRelationships());
        }
        dto.setPosition(entity.getPosition());
        return dto;
    }

    @Override
    public Domain convertToEntity(EntityDto dto) {
        Domain entity = new Domain();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDescription("");
        if (dto.getFields() != null && !dto.getFields().isEmpty()) {
            entity.setAttributes(fieldMapper.convertToEntityList(dto.getFields()));
        }
        if (dto.getRelationships() != null && !dto.getRelationships().isEmpty()) {
            entity.setRelationships(dto.getRelationships());
        }
        entity.setPosition(dto.getPosition());
        return entity;
    }

    public List<Domain> addParent(List<Domain> entities, App parent) {
        if (entities != null) {
            for (Domain entity : entities) {
                entity.setApp(parent);
                addParentToEnumDef(parent, entity.getAttributes());
            }
        }
        return entities;
    }

    public Domain addParent(Domain entity, App parent) {
        if (entity != null) {
            entity.setApp(parent);
            addParentToEnumDef(parent, entity.getAttributes());
        }
        return entity;
    }

    private void addParentToEnumDef(App parent, List<Attribute> attributes) {
        if (attributes != null) {
            for (Attribute attribute : attributes) {
                attribute.setEnumDef(enumMapper.addParent(attribute.getEnumDef(), parent));
            }
        }
    }
}
