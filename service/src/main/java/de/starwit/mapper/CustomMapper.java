package de.starwit.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.starwit.persistence.entity.AbstractEntity;

public interface CustomMapper<ENTITY extends AbstractEntity<Long>, DTO extends AbstractEntity<Long>> {

    public  DTO convertToDto(ENTITY entity);
    
    public ENTITY convertToEntity(DTO dto);

   default public List<DTO> convertToDtoList(Collection<ENTITY> entities) {
        List<DTO> dtos = null;
        if (entities != null) {
            dtos = new ArrayList<>();
            for (ENTITY entity  : entities) {
                dtos.add(convertToDto(entity));
            }
        }
        return dtos;
    }

    default public List<ENTITY> convertToEntityList(Collection<DTO> dtos) {
        List<ENTITY> entities = null;
        if (dtos != null) {
            entities = new ArrayList<>();
            for (DTO dto  : dtos) {
                entities.add(convertToEntity(dto));
            }
        }
        return entities;
    }

    default public Set<ENTITY> convertToEntitySet(Collection<DTO> dtos) {
        Set<ENTITY> entities = null;
        if (dtos != null) {
            entities = new HashSet<>();
            for (DTO dto  : dtos) {
                entities.add(convertToEntity(dto));
            }
        }
        return entities;
    }
    
}
