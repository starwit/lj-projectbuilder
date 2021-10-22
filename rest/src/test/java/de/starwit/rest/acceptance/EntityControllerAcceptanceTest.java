package de.starwit.rest.acceptance;

import org.junit.Test;

import de.starwit.dto.EntityDto;

public class EntityControllerAcceptanceTest extends AbstractControllerAcceptanceTest<EntityDto> {

    private static final String restpath = "/api/entities/";

    @Override
    @Test
    public Class<EntityDto> getDtoClass() {
        return EntityDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    @Test
    public void canCreate() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Test
    public void canRetrieveById() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Test
    public void canUpdate() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Test
    public void canDelete() throws Exception {
        // TODO Auto-generated method stub
        
    }

  
}
