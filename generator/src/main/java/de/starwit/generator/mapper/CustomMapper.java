package de.starwit.generator.mapper;

import de.starwit.persistence.entity.AbstractEntity;

public interface CustomMapper<ENTITY extends AbstractEntity<Long>, DTO extends AbstractEntity<Long>> {

    public  DTO convertDb(ENTITY entity) throws Exception;
    
    public ENTITY convertFrontend(DTO dto) throws Exception;
    
}
