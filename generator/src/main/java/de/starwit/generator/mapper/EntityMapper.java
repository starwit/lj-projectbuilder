package de.starwit.generator.mapper;

import org.springframework.stereotype.Component;

import de.starwit.generator.dto.EntityDto;
import de.starwit.persistence.entity.Domain;

@Component
public class EntityMapper implements CustomMapper<Domain, EntityDto> {

    @Override
    public EntityDto convertDb(Domain entity) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Domain convertFrontend(EntityDto dto) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    
}
