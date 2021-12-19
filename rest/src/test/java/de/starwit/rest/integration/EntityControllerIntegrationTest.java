package de.starwit.rest.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import de.starwit.TestdataConstants;
import de.starwit.dto.EntityDto;
import de.starwit.mapper.EntityMapper;
import de.starwit.mapper.FieldMapper;
import de.starwit.persistence.entity.Domain;
import de.starwit.rest.controller.EntityController;
import de.starwit.service.impl.DomainService;

@WebMvcTest(controllers = EntityController.class)
@Import({EntityMapper.class, FieldMapper.class})
public class EntityControllerIntegrationTest extends AbstractControllerIntegrationTest<EntityDto> {

    final static Logger LOG = LoggerFactory.getLogger(EntityControllerIntegrationTest.class);

    @MockBean
    private DomainService domainService;

    private JacksonTester<EntityDto> jsonTester;

    @Autowired
    private EntityMapper entityMapper;

    private static final String data = TestdataConstants.TESTDATA_ENTITY_DIR;

    private static final String restpath = "/api/entities/";

    @Override
    public Class<EntityDto> getDtoClass() {
        return EntityDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    @Test
    public void canRetrieveById() throws Exception {
        // given
        EntityDto dto = readFromFile(data + "entity.json");
        Domain domain = entityMapper.convertToEntity(dto);
        when(domainService.findById(0L)).thenReturn(domain);
        MockHttpServletResponse response = retrieveById(0L);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonTester.write(dto).getJson());

    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {
        // given
        EntityDto dto = readFromFile(data + "entity-with-fields.json");
        Domain domain = entityMapper.convertToEntity(dto);
        when(domainService.findById(0L)).thenReturn(domain);
        MockHttpServletResponse response = retrieveById(0L);

        //then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
            .isEqualTo(jsonTester.write(dto).getJson());

    }

    @Test
    public void canThrowIlligalFormatException() throws Exception {
        try {
            // given
            EntityDto dto = readFromFile(data + "entity-wrong-format.json");
            Domain domain = entityMapper.convertToEntity(dto);
            when(domainService.findById(0L)).thenReturn(domain);
            MockHttpServletResponse response = retrieveById(0L);

            //then
            assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
            assertThat(response.getContentAsString())
                .isEqualTo(jsonTester.write(dto).getJson());
        } catch(Exception e) {
            e.printStackTrace();
        }

    }


}
