package de.starwit.rest.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import de.starwit.TestdataConstants;
import de.starwit.dto.SaveAppTemplateDto;
import de.starwit.persistence.entity.AppTemplate;

public class AppTemplateControllerAcceptanceTest extends AbstractControllerAcceptanceTest<SaveAppTemplateDto> {

    final static Logger LOG = LoggerFactory.getLogger(AppControllerAcceptanceTest.class);

    private static final String data = TestdataConstants.TESTDATA_APPTEMPLATE_DIR;

    private static final String restpath = "/api/apptemplates/";

    private JacksonTester<SaveAppTemplateDto> jsonTester;

    @Override
    public Class<SaveAppTemplateDto> getDtoClass() {
        return SaveAppTemplateDto.class;
    }

    @Override
    public String getRestPath() {
        return restpath;
    }

    @Override
    public JacksonTester<SaveAppTemplateDto> getJsonTester() {
        return jsonTester;
    }

    @Test
    public void canCreate() throws Exception {
        // given
        SaveAppTemplateDto dto = readFromFile(data + "apptemplate.json");
  
        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        SaveAppTemplateDto dtoresult = mapper.readValue(response.getContentAsString(), SaveAppTemplateDto.class);
        assertThat(dtoresult.getLocation()).isEqualTo("https://github.com/starwit/project-templates.git");
        assertThat(dtoresult.getBranch()).isEqualTo("v2");
    }

    @Test
    public void isValidated() throws Exception {
        // given
        SaveAppTemplateDto dto = readFromFile(data + "apptemplate-wrong.json");

        // when
        MockHttpServletResponse response = create(dto);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void canNotFindById() throws Exception {
        // when
        MockHttpServletResponse response = mvc
                .perform(get(getRestPath() + "/4242").contentType(MediaType.APPLICATION_JSON)).andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void canRetrieveById() throws Exception {
        // given
        SaveAppTemplateDto dto = readFromFile(data + "apptemplate.json");
        MockHttpServletResponse response = create(dto);
        SaveAppTemplateDto dto2 = mapper.readValue(response.getContentAsString(), SaveAppTemplateDto.class);

        // when
        response = retrieveById(dto2.getId());

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        AppTemplate dtoresult = mapper.readValue(response.getContentAsString(), AppTemplate.class);
        assertThat(dtoresult.getLocation()).isEqualTo("https://github.com/starwit/project-templates.git");
        assertThat(dtoresult.getBranch()).isEqualTo("v2");
    }

    @Test
    public void canUpdate() throws Exception {

        // given
        SaveAppTemplateDto dto = readFromFile(data + "apptemplate.json");
        MockHttpServletResponse response = create(dto);
        SaveAppTemplateDto dto2 = mapper.readValue(response.getContentAsString(), SaveAppTemplateDto.class);
        dto2.setBranch("testtest");

        // when
        response = update(dto2);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        SaveAppTemplateDto result = mapper.readValue(response.getContentAsString(), SaveAppTemplateDto.class);
        assertThat(result.getId()).isEqualTo(dto2.getId());
        assertThat(result.getBranch()).isEqualTo(dto2.getBranch());
    }

    @Override
    @Test
    public void canDelete() throws Exception {
        // given
        SaveAppTemplateDto dto = readFromFile(data + "apptemplate.json");
        MockHttpServletResponse response = create(dto);
        SaveAppTemplateDto dto2 = mapper.readValue(response.getContentAsString(), SaveAppTemplateDto.class);
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
