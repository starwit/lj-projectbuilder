package de.starwit.rest.integration;

import de.starwit.TestdataConstants;
import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.ApplicationDto;
import de.starwit.mapper.ApplicationMapper;
import de.starwit.mapper.EntityMapper;
import de.starwit.mapper.FieldMapper;
import de.starwit.persistence.entity.App;
import de.starwit.rest.controller.ApplicationController;
import de.starwit.service.impl.AppService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ApplicationController.class)
@Import({ApplicationMapper.class, EntityMapper.class, FieldMapper.class})
public class ApplicationControllerIntegrationTest extends AbstractControllerIntegrationTest<ApplicationDto> {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationControllerIntegrationTest.class);
    private static final String data = TestdataConstants.TESTDATA_APP_DIR;
    private static final String restpath = "/api/apps/";
    @MockBean
    private AppService appService;
    private JacksonTester<ApplicationDto> jsonApplicationDto;
    @Autowired
    private ApplicationMapper applicationMapper;

    @Override
    public Class<ApplicationDto> getDtoClass() {
        return ApplicationDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Test
    public void canRetrieveById() throws Exception {

        ApplicationDto dto = readFromFile(data + "app.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        MockHttpServletResponse response = retrieveById(0L);

        //then
        AppTemplateDto appTemplateDto = applicationMapper.convertToDto(entity).getTemplate();
        //set default tempate
        dto.setTemplate(appTemplateDto);
        assertNotNull(appTemplateDto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {

        // given
        ApplicationDto dto = readFromFile(data + "app-with-fields.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        MockHttpServletResponse response = retrieveById(0L);

        //then
        AppTemplateDto appTemplateDto = applicationMapper.convertToDto(entity).getTemplate();
        //set default tempate
        dto.setTemplate(appTemplateDto);
        assertNotNull(appTemplateDto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }

    @Test
    public void canRetrieveByIdWithRelations() throws Exception {

        ApplicationDto dto = readFromFile(data + "app-with-relations.json");
        App entity = applicationMapper.convertToEntity(dto);
        when(appService.findById(0L)).thenReturn(entity);

        //when
        MockHttpServletResponse response = retrieveById(0L);

        //then
        AppTemplateDto appTemplateDto = applicationMapper.convertToDto(entity).getTemplate();
        //set default tempate
        dto.setTemplate(appTemplateDto);
        assertNotNull(appTemplateDto);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(jsonApplicationDto.write(dto).getJson());
    }
}
