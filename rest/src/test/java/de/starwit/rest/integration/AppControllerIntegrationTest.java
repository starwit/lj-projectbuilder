package de.starwit.rest.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.AppDto;
import de.starwit.mapper.AppMapper;
import de.starwit.mapper.EntityMapper;
import de.starwit.mapper.FieldMapper;
import de.starwit.persistence.entity.App;
import de.starwit.rest.controller.AppController;
import de.starwit.service.impl.AppService;

@WebMvcTest(controllers = AppController.class)
@Import({ AppMapper.class, EntityMapper.class, FieldMapper.class })
public class AppControllerIntegrationTest extends AbstractControllerIntegrationTest<AppDto> {

    final static Logger LOG = LoggerFactory.getLogger(AppControllerIntegrationTest.class);

    @MockBean
    private AppService appService;

    private JacksonTester<AppDto> jsonAppDto;

    @Autowired
    private AppMapper applicationMapper;

    private static final String data = TestdataConstants.TESTDATA_APP_DIR;

    private static final String restpath = "/api/apps/";

    @Override
    public Class<AppDto> getDtoClass() {
        return AppDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Test
    public void canRetrieveById() throws Exception {

        AppDto dto = readFromFile(data + "app.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        MockHttpServletResponse response = retrieveById(0L);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonAppDto.write(dto).getJson());
    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {

        // given
        AppDto dto = readFromFile(data + "app-with-fields.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        MockHttpServletResponse response = retrieveById(0L);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonAppDto.write(dto).getJson());
    }

    @Test
    public void canRetrieveByIdWithRelations() throws Exception {

        AppDto dto = readFromFile(data + "app-with-relations.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        // when
        MockHttpServletResponse response = retrieveById(0L);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonAppDto.write(dto).getJson());
    }
}
