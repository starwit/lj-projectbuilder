package de.starwit.rest.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import de.starwit.TestdataConstants;
import de.starwit.dto.AppTemplateDto;
import de.starwit.dto.ApplicationDto;
import de.starwit.dto.EntityDto;

public class ApplicationControllerAcceptanceTest extends AbstractControllerAcceptanceTest<ApplicationDto> {

    final static Logger LOG = LoggerFactory.getLogger(ApplicationControllerAcceptanceTest.class);

    private static final String data = TestdataConstants.TESTDATA_APP_DIR;

    private static final String restpath = "/api/apps/";

    private JacksonTester<ApplicationDto> jsonTester;

    @Override
    public Class<ApplicationDto> getDtoClass() {
        return ApplicationDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    public JacksonTester<ApplicationDto> getJsonTester() {
        return jsonTester;
    }

    @Test
    public void canCreate() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app.json");
        AppTemplateDto templateDto = new AppTemplateDto();
        templateDto.setId(1L);
        templateDto.setName("lirejarp");
        dto.setTemplate(templateDto);

        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        JsonNode jsonNode = mapper.readTree(response.getContentAsString());
        Long id = jsonNode.get("id").asLong();
        dto.setId(id);
        String applicationString = jsonTester.write(dto).getJson();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(applicationString);
    }

    @Test
    public void isValidated() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app-wrong-format.json");
        AppTemplateDto templateDto = new AppTemplateDto();
        templateDto.setId(1L);
        templateDto.setName("lirejarp");
        dto.setTemplate(templateDto);

        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"entities[0].name\":\"must not be blank\"}");
    }

    @Test
    public void isValidatedInputParam() throws Exception {
        // when
        MockHttpServletResponse response = mvc
                .perform(get(getRestPath() + "/wrongvalue").contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        // then
        LOG.info(response.getContentAsString());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                "Wrong input value wrongvalue. Failed to convert value of type String to required type Long.");
    }

    @Test
    public void canNotFindById() throws Exception {
        // when
        MockHttpServletResponse response = mvc
                .perform(get(getRestPath() + "/4242").contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEqualTo("App not found.");
    }

    @Test
    public void canRetrieveById() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Test
    public void canRetrieveByIdWithFields() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(jsonTester.write(result).getJson()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Test
    public void canRetrieveByIdWithRelations() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app-with-relations.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(jsonTester.write(result).getJson()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Test
    public void canUpdate() throws Exception {

        // given
        ApplicationDto dto = readFromFile(data + "app-with-fields.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        dto2.setBaseName("secondName");
        EntityDto entityDto = dto2.getEntities().get(0);
        entityDto.setName("SecondEntityName");
        int fieldcount = entityDto.getFields().size();
        entityDto.getFields().get(0).setFieldName("secondFieldName");
        entityDto.getFields().remove(1);

        // when
        response = update(dto2);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        ApplicationDto result = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        assertThat(result.getId()).isEqualTo(dto2.getId());
        assertThat(result.getBaseName()).isEqualTo("secondName");
        assertThat(result.getEntities().get(0).getFields().size()).isEqualTo(fieldcount - 1);
        assertThat(result.getEntities().get(0).getId()).isEqualTo(entityDto.getId());
        assertThat(result.getEntities().get(0).getName()).isEqualTo("SecondEntityName");
        assertThat(result.getEntities().get(0).getFields().get(0).getId())
                .isEqualTo(entityDto.getFields().get(0).getId());
        assertThat(result.getEntities().get(0).getFields().get(0).getFieldName()).isEqualTo("secondFieldName");

        assertThat(response.getContentAsString()).isEqualTo(jsonTester.write(dto2).getJson());
    }

    @Override
    @Test
    public void canDelete() throws Exception {
        // given
        ApplicationDto dto = readFromFile(data + "app.json");
        MockHttpServletResponse response = create(dto);
        ApplicationDto dto2 = mapper.readValue(response.getContentAsString(), ApplicationDto.class);
        response = retrieveById(dto2.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        // when
        delete(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        response = retrieveById(dto2.getId());
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
